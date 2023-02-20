# Solve the problem from the first set here
# Generate the first prime number larger than a given number`n`. Problem 1


def read_number():
    n = int(input('Enter a number:'))
    return n


def prime_number(n):
    if n <= 1:  # the numbers 0 and 1 are not prime
        return False
    elif n == 2:  # the only even prime number
        return True
    elif n % 2 == 0:  # even numbers, except 2, are not prime
        return False
    else:
        i = 3  # verifying if the following odd numbers are prime
        while i*i <= n:
            if n % i == 0:
                return False
            else:
                i += 2
    return True


def first_prime_number(n):
    n += 1  # the found number should be larger than n

    while not prime_number(n):  # the algorithm stops when the first prime number is found
        n += 1

    return n


print('The first prime number larger than n is: ', first_prime_number(read_number()))


