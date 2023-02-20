# Solve the problem from the third set here
"""
Generate the largest perfect number smaller than a given natural number n. If such a number does not exist, a message
should be displayed. A number is perfect if it is equal to the sum of its divisors, except itself. (e.g. 6 is a perfect
number, as 6=1+2+3).
"""


# Problem 15

def read_number():
    n = int(input('Enter a number:'))
    return n


def verify_perfect_number(n):
    s = 1  # initializing the sum of the divisors with 1, as every number has it
    for i in range(2, n // 2 + 1):
        if n % i == 0:
            s += i

    if s == n:  # here we verify if the number is equal with its sum of divisors
        return True
    return False


def find_number(n):
    for i in range(n - 1, 1, -1):
        if verify_perfect_number(i):
            return i
    return False


f = read_number()

if find_number(f):
    print('The largest perfect number smaller than n is: ', find_number(f))
else:
    print('There are no perfect numbers smaller than n')
