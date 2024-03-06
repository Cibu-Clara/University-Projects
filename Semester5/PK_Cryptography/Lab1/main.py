import time


# Euclidean Algorithm
def gcd_euclidean(a, b):
    while b:
        a, b = b, a % b
    return a


# Brute Force Algorithm
def gcd_brute_force(a, b):
    if a == 0 or a == b:
        return b
    elif b == 0:
        return a
    gcd = 1
    for d in range(2, min(a, b) + 1):
        if a % d == 0 and b % d == 0:
            gcd = d
    return gcd


# Prime Factors Algorithm
def gcd_prime_factors(a, b):
    if a == 0 or a == b:
        return b
    elif b == 0:
        return a
    d = 2
    gcd = 1
    while a > d or b > d:
        while a % d == 0 and b % d == 0:
            gcd *= d
            a //= d
            b //= d
        d += 1
    return gcd


# Test the algorithms and measure their running times
input_numbers = [(0, 75), (48, 18), (30, 17), (255, 177), (101, 301), (12345, 67890), (4137524, 1227244),
                 (182364, 116033), (10662, 78376), (123456789, 987654321)]

algorithms = [gcd_euclidean, gcd_brute_force, gcd_prime_factors]
algorithm_names = ["Euclidean Algorithm", "Brute Force Algorithm", "Prime Factors Algorithm"]

for i, numbers in enumerate(input_numbers):
    print(f"Input {i + 1}: ({numbers[0]}, {numbers[1]})")
    for algorithm, name in zip(algorithms, algorithm_names):
        start_time = time.time()
        result = algorithm(numbers[0], numbers[1])
        end_time = time.time()
        elapsed_time = end_time - start_time
        print(f"{name}: GCD = {result}, Time elapsed = {elapsed_time:.8f} seconds")
    print()


print("Euclidian Algorithm for big size numbers:\n")

big_size_numbers = [(999999999999999, 1), (9876543210, 1234567890), (123456789012345, 987654321098765),
                    (123456789012345678, 987654321098765432), (77777777777777777777, 33333333333333333333),
                    (12345678901234567890123456789, 98765432109876543210987654321)]

for i, numbers in enumerate(big_size_numbers):
    print(f"Input {i + 1}: ({numbers[0]}, {numbers[1]})")
    start_time = time.time()
    result = gcd_euclidean(numbers[0], numbers[1])
    end_time = time.time()
    elapsed_time = end_time - start_time
    print(f"Euclidian Algorithm: GCD = {result}, Time elapsed = {elapsed_time:.8f} seconds")
    print()
