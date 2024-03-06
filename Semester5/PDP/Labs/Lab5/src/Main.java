import java.util.Arrays;
import java.util.concurrent.*;

class Multiplication {

    private final int[] array1;
    private final int[] array2;
    private final int length_array1;
    private final int length_array2;

    public Multiplication(int[] array1, int[] array2) {
        this.array1 = array1;
        this.array2 = array2;
        this.length_array1 = this.array1.length;
        this.length_array2 = this.array2.length;
    }

    public int[] multiply_normally() {
        int value = this.length_array1 + this.length_array2 - 1;
        int[] prod = new int[value];

        // Initialize the product polynomial
        for (int i = 0; i < value; i++)
            prod[i] = 0;

        // Multiply two polynomials term by term
        // Take every term of first polynomial
        for (int i = 0; i < this.length_array1; i++) {
            // Multiply the current term of first polynomial with every term of second polynomial.
            for (int j = 0; j < this.length_array2; j++)
                prod[i + j] += array1[i] * array2[j];
        }
        return prod;
    }
    public int[] multiplyParallel(int numThreads) {
         //  the tasks are sectioned based on the position in the result polynomial being calculated
        // (represented by the index variable). like thread 1 calculates result[0] , thread 2 calculates result[1]
        // //and so on
        int value = this.length_array1 + this.length_array2 - 1;
        int[] prod = new int[value];

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            Future<int[]>[] partialResults = new Future[value];

            for (int i = 0; i < value; i++) {
                final int index = i;
                partialResults[i] = executor.submit(new Callable<int[]>() {
                    @Override
                    public int[] call() {
                        int[] partialProd = new int[length_array1];
                        for (int j = 0; j < length_array1; j++) {
                            if (index - j >= 0 && index - j < length_array2) {
                                partialProd[j] = array1[j] * array2[index - j];
                            }
                        }
                        return partialProd;
                    }
                });
            }

            for (int i = 0; i < value; i++) {
                try {
                    int[] partialProd = partialResults[i].get();
                    for (int j = 0; j < length_array1; j++) {
                        prod[i] += partialProd[j];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            executor.shutdown();
        }
        return prod;
    }
}
class KaratsubaPolynomialMultiplication {
    public static long[] simpleMultiply(long[] A, long[] B) {
        int size1 = A.length;
        int size2 = B.length;
        int resultSize = size1 + size2 - 1;
        long[] result = new long[resultSize];

        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                result[i + j] += A[i] * B[j];
            }
        }
        return result;
    }
    public static long[] karatsubaMultiply(long[] A, long[] B) {
        // for small numbers we shouldn't use this version , but the O(n^2) one
        // take the length of each vector
        int size1 = A.length;
        int size2 = B.length;

        // get the maximum size from the vectors
        int N = Math.max(size1, size2);
        if (N < 10) {
            return simpleMultiply(A, B);
        }

        //The variable N in the code represents the size of the coefficients. When you calculate Math.pow(10, N),
        // you effectively get a number that has N decimal places. This value helps in separating the coefficients
        // into two parts: the higher-order coefficients and the lower-order coefficients. For example, if N is 3,
        // Math.pow(10, N) gives 1000, so coefficients can be divided into thousands, hundreds, tens, and ones.
        N = (N / 2) + (N % 2);

       // ex : A={0,1,2}
        long[] b = splitArray(A, N);   //b={1,2}
        long[] a = removeMSBs(A, N);   // a= {0}
        long[] d = splitArray(B, N);
        long[] c = removeMSBs(B, N);

        long[] z0 = karatsubaMultiply(a, c);
        long[] z1 = karatsubaMultiply(add(a, b), add(c, d));
        long[] z2 = karatsubaMultiply(b, d);

        long[] result = add(z0, shiftLeft(subtract(subtract(z1, z0), z2), N));
        result = add(result, shiftLeft(z2, 2 * N));

        return result;
    }

    public static long[] parallelKaratsubaMultiply(long[] A, long[] B, int numThreads) {
        int size1 = A.length;
        int size2 = B.length;
        int resultSize = size1 + size2 - 1;
        long[] result = new long[resultSize];

        int M = Math.max(size1, size2);
        if (M < 10) {
            return simpleMultiply(A, B);
        }

        int N = Math.max(size1, size2) / 2;

        long[] b = splitArray(A, N);
        long[] a = removeMSBs(A, N);
        long[] d = splitArray(B, N);
        long[] c = removeMSBs(B, N);

        long[] z0, z1, z2;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            Future<long[]> z0Future = executor.submit(() -> parallelKaratsubaMultiply(a, c, numThreads));
            Future<long[]> z1Future = executor.submit(() -> parallelKaratsubaMultiply(add(a, b), add(c, d), numThreads));
            Future<long[]> z2Future = executor.submit(() -> parallelKaratsubaMultiply(b, d, numThreads));

            z0 = z0Future.get();
            z1 = z1Future.get();
            z2 = z2Future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new long[resultSize];
        } finally {
            executor.shutdown();
        }

        result = add(z0, shiftLeft(subtract(subtract(z1, z0), z2), N));
        result = add(result, shiftLeft(z2, 2 * N));

        return result;
    }

    // we split the arrays in smaller ones just like divide and conquer
    public static long[] splitArray(long[] A, int n) {
        int size = A.length;
        if (n >= size) {
            return A;
        }

        long[] result = new long[n];
        System.arraycopy(A, size - n, result, 0, n);
        return result;
    }

    //original array A: {3, 1, 2}. n= 2
    //Remove 2 MSBs, leaving {3} as the last coefficient.
    public static long[] removeMSBs(long[] A, int n) {
        int size = A.length;
        if (n >= size) {
            return new long[] { 0 };
        }

        long[] result = new long[size - n];
        System.arraycopy(A, 0, result, 0, size - n); // we copy size-n elements from A to result
        return result;
    }

    // creates a  new result array containing a1+b1 , a2+b2, a3+b3 etc
    public static long[] add(long[] A, long[] B) {
        int sizeA = A.length;
        int sizeB = B.length;
        int resultSize = Math.max(sizeA, sizeB);   //we get the maxsize because the polyniomials might be of different degrees
        long[] result = new long[resultSize];

        for (int i = 0; i < sizeA; i++) {
            result[i] += A[i];
        }
        for (int i = 0; i < sizeB; i++) {
            result[i] += B[i];
        }

        return result;
    }

    // creates a  new result array containing a1-b1 , a2-b2, a3-b3 etc
    public static long[] subtract(long[] A, long[] B) {
        int sizeA = A.length;
        int sizeB = B.length;
        int resultSize = Math.max(sizeA, sizeB);
        long[] result = new long[resultSize];

        for (int i = 0; i < sizeA; i++) {
            result[i] += A[i];
        }
        for (int i = 0; i < sizeB; i++) {
            result[i] -= B[i];
        }

        return result;
    }

    // adds new available spaces to the array by moving the elements to the left with shift number of positions
    // ex [1,2,3,4] - > shift = 2 -> [0,0,1,2,3,4]
    public static long[] shiftLeft(long[] A, int shift) {
        int size = A.length;
        long[] result = new long[size + shift];
        System.arraycopy(A, 0, result, shift, size);
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] polynomial1 = {3, 1, 2, 2, 0, 1, 4, 5, 2, 10};
        int[] polynomial2 = {2, 0, 1, 4, 5, 6, 8, 9, 1, 1};

        Multiplication multiplication = new Multiplication(polynomial1, polynomial2);
        KaratsubaPolynomialMultiplication karatsubaMultiplication = new KaratsubaPolynomialMultiplication();

        int[] product = multiplication.multiply_normally();
        System.out.println("Polynomial Multiplication (Normal Method): " + Arrays.toString(product));

        int[] product_threads = multiplication.multiplyParallel(8);
        System.out.println("Polynomial Multiplication (Parallel Method): " + Arrays.toString(product_threads));

        long[] longPolynomial1 = {3, 1, 2, 2, 0, 1, 4, 5, 2, 10};
        long[] longPolynomial2 = {2, 0, 1, 4, 5, 6, 8, 9, 1, 1};
        long[] karatsubaProduct = karatsubaMultiplication.karatsubaMultiply(longPolynomial1, longPolynomial2);
        System.out.println("Polynomial Multiplication (Karatsuba Method): " + Arrays.toString(karatsubaProduct));

        long[] karatsubaProduct_threads = karatsubaMultiplication.parallelKaratsubaMultiply(longPolynomial1, longPolynomial2,8);
        System.out.println("Polynomial Multiplication (Karatsuba Method) using threads: " + Arrays.toString(karatsubaProduct_threads));
    }
}