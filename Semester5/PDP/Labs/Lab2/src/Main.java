import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ScalarProductContainer {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final int[] products;
    int size;
    int index = 0;

    public ScalarProductContainer(int n) {
        products = new int[n];
        this.size = n;
    }

    public void addItem(int product) throws InterruptedException {
        System.out.println("Hello from Producer thread...");
        lock.lock();
        if (index < size) {
            products[index] = product;
            index++;
            condition.await();
        }
        lock.unlock();
    }

    public int getItem() {
        System.out.println("Hello from Consumer thread...");
        lock.lock();
        int newItem = products[index - 1];
        condition.signal();
        lock.unlock();
        return newItem;
    }
}

class Producer implements Runnable {
    private final int[] v1;
    private final int[] v2;
    ScalarProductContainer products;

    public Producer(int[] v1, int[] v2, ScalarProductContainer products) {
        this.v1 = v1;
        this.v2 = v2;
        this.products = products;
    }

    @Override
    public void run() {
        for (int i = 0;i < v1.length; i++) {
            int product = v1[i] * v2[i];
            try {
                products.addItem(product);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer implements Runnable{
    private final int[] v1;
    private final ScalarProductContainer products ;
    private int result = 0;

    public Consumer(int[] v ,ScalarProductContainer products) {
        this.v1 = v;
        this.products = products;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int nr = 0;
        while(nr < v1.length) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int newProduct = products.getItem();
            result += newProduct;
            nr++;
        }
        System.out.println(result);
    }
}

public class Main {
    public static void main(String[] args) {

        int[] v1 = {1, 2, 3, 4, 5};
        int[] v2 = {10, 20, 30, 40, 50};

        // the scalar product will be: v1*v2 = 1*10 + 2*20 + 3*30 + 4*40 + 5*50 = 550
        ScalarProductContainer container = new ScalarProductContainer(5);

        Thread producer = new Thread(new Producer(v1, v2, container));
        Thread consumer = new Thread(new Consumer(v1, container));

        producer.start();
        consumer.start();

        try{
            producer.join();
            consumer.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}