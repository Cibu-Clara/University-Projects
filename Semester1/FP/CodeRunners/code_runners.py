"""
Implement the solution here. 
You may add other source files.
Make sure you commit & push the source code before the end of the test.

Solutions using user-defined classes will not be graded.
"""
from random import randint
from time import time


# UI section
def introduce_number():
    n = int(input("Enter a number: "))
    if n < 1000 or n > 9999:
        raise ValueError("Number must have 4 digits - Game over! Computer wins!")
    if has_distinct_digits(n) is False and n != 8086:
        raise ValueError("Number must have distinct digits - Game over! Computer wins!")
    return n


def run():
    secret_number = generate_secret_number()
    start = time()
    try:
        while True:
            n = introduce_number()
            if n == secret_number:
                print("You win!")
                break
            if time() - start > 60:
                print("Time out! Computer wins!")
                break
            if n == 8086:
                print("Selected number is: ", secret_number)
            if n != 8086:
                print(report_codes(secret_number, n), "codes and ", report_runners(secret_number, n) -
                      report_codes(secret_number, n), "runners")

    except ValueError as ve:
        print(str(ve))


# non-UI section
def report_codes(secret_number, n):
    codes = 0
    while secret_number > 0 and n > 0:
        if secret_number % 10 == n % 10:
            codes += 1
        secret_number //= 10
        n //= 10
    return codes


def test_report_codes():
    assert report_codes(1789, 1085) == 2


def report_runners(secret_number, n):
    runners = 0
    while n > 0:
        if str(n % 10) in str(secret_number):
            runners += 1
        n //= 10
    return runners


def test_report_runners():
    assert report_runners(1782, 2018) == 3


def generate_secret_number():
    """
    The function generates random 4-digit numbers until one with distinct digits is generated
    :return: the 4-digit number with distinct digits
    """
    ok = 0
    while ok == 0:
        n = randint(1000, 9999)
        if has_distinct_digits(n) is True:
            return n


def has_distinct_digits(n):
    digit_list = []
    for i in str(n):
        if i in digit_list:
            return False
        digit_list.append(i)
    return True


def test_has_distinct_digits():
    assert has_distinct_digits(1234)


test_has_distinct_digits()
test_report_codes()
test_report_runners()
run()
