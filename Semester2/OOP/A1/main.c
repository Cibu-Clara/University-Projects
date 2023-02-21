#include <stdio.h>
/*
2. 
a. Generate the first n prime numbers (n is a given natural number).
b. Given a vector of numbers, find the longest contiguous subsequence such that any two consecutive 
elements are relatively prime.*/


//non-UI section
int prime(int n)
/*
A function which verifies if a natural number is prime or not.
input: the number n
output: 1, if the number is prime, 0 otherwise
*/
{
	if (n <= 1)
		return 0;
	if (n % 2 == 0 && n != 2)
		return 0;
	for (int i = 3; i * i <= n; i += 2)
		if (n % i == 0)
			return 0;
	return 1;
}


int gcd(int m, int n)
/*
A function which computes the greatest common divisor between two numbers.
input: the two numbers, m and n
output: the greatest common divisor
*/
{
	while (n != 0)
	{
		int r = m % n;
		m = n;
		n = r;
	}
	if (m < 0)
		m = -m;
	return m;
}

void longestSequence(int v[], int len, int* start, int* end)
/*
The function finds the longest contiguous subsequence such that any two consecutive elements are relatively prime, by using the
function "gcd".
input: the vector v, the length len
output: the positions start and end of the subsequence
*/
{
	int maxx = 1, aux[100], i;
	aux[0] = 1;
	for (i = 1; i < len; i++)
		if (gcd(v[i - 1], v[i]) == 1)
		{
			aux[i] = aux[i - 1] + 1;
			if (aux[i] > maxx)
				maxx = aux[i], * end = i, * start = *end - aux[i] + 1;
		}
		else
			aux[i] = 1;
}

//UI section
void reading(int v[], int* len)
/*
A function which reads from the console the length of a vector, as well as its elements.
input: the vector v, the length len
output: the vector v, the length len
*/
{
	printf("Enter the length of the vector:\n");
	scanf_s("%d", len);
	while (*len <= 1)
	{
		printf("Vector must have at least two elements!\nEnter the length of the vector:\n");
		scanf_s("%d", len);
	}
	printf("Enter the elements of the vector:\n");
	for (int i = 0; i < *len; i++)
		scanf_s("%d", &v[i]);
}

void firstPrimeNumbers(int n)
/*
The function checks and prints the first n prime numbers.
input: the number n
output: -
*/
{
	int i = 2;
	int cnt = 0;
	printf("The first %d prime numbers are:\n", n);
	while (cnt < n)
	{
		if (prime(i))
			printf("%d ", i), cnt++;
		i++;
	}
	printf("\n");
}

void primeNumbersSmaller(int n)
/*
The function checks and prints all prime numbers smaller than n.
input: the natural number n
output: -
*/
{
	printf("The prime numbers smaller than %d are:\n", n);
	for (int i = 2; i < n; i++)
		if (prime(i))
			printf("%d ", i);
	printf("\n");
}

void printing(int v[], int start, int end)
/*
A function which prints the longest contiguous subsequence such that any two consecutive elements are relatively prime.
input: the vector v, the positions start and end of the subsequence
output: -
*/
{
	printf("The longest subsequence with the given property is:\n");
	for (int i = start; i <= end; i++)
		printf("%d ", v[i]);
	printf("\n");
}

int main()
{
	int v[100];
	int len = 0;
	int ok = 0;
	while (ok == 0)
	{
		printf("\nPlease select option:\n1. Read a vectors of numbers\n2. Generate the first n prime numbers.\n3. Find the longest contiguous subsequence such that any two consecutive elements of the vector are relatively prime.\n4. Generate all the prime numbers smaller than a given natural number n.\n5. Exit\n");
		int option;
		scanf_s("%d", &option);
		if (option == 1)
		{
			reading(v, &len);
		}
		else if (option == 2)
		{
			int n;
			printf("Enter a natural number:\n");
			scanf_s("%d", &n);
			firstPrimeNumbers(n);
		}
		else if (option == 3)
		{
			int start, end;
			longestSequence(v, len, &start, &end);
			printing(v, start, end);
		}
		else if (option == 4)
		{
			int n;
			printf("Enter a natural number:\n");
			scanf_s("%d", &n);
			primeNumbersSmaller(n);
		}
		else if (option == 5)
			ok = 1;
		else
			printf("Invalid option!\n");
	}

	return 0;
}
