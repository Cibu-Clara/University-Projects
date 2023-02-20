# Solve the problem from the second set here
"""
The numbers n1 and n2 have the property P if their writing in base 10 uses the same digits (e.g. 2113 and 323121).
Determine whether two given natural numbers have property P
"""
# Problem 11


def read_numbers():
    n1 = int(input('Enter the first number:'))
    n2 = int(input('Enter the second number:'))
    f1 = digits(n1)
    f2 = digits(n2)
    if verify_digits(f1, f2):
        print('The two numbers have the property P')
    else:
        print('The two numbers do not have the property P')


def digits(n):
    fr = [0] * 10  # initializing the list of digits
    while n > 0:
        fr[int(n % 10)] = 1  # for every digit found in n
        n = int(n/10)
    return fr


def verify_digits(f1, f2):

    for i in range(0, len(f1)):
        if f1[i] != f2[i]:  # if one digit with different frequency is found
            return False
    return True


read_numbers()






