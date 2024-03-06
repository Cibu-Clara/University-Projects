import math


def is_square(x):
    sqrt_x = int(math.sqrt(x))
    return sqrt_x**2 == x


def is_prime(n):
    if n <= 1:
        return False
    if n <= 3:
        return True
    if n % 2 == 0:
        return False

    i = 5
    while i * i <= n:
        if n % i == 0:
            return False
        i += 2

    return True


def is_odd_composite_non_square(n):
    return n % 2 == 1 and not is_prime(n) and not is_square(n)


def generalized_fermat_algorithm(n, bound):
    if not is_odd_composite_non_square(n):
        raise ValueError("Input must be an odd composite number that is not a square.")
    k = 1
    while True:
        print(k)
        t0 = int(math.sqrt(k * n))
        for t in range(t0 + 1, t0 + bound + 1):
            s_squared = t**2 - k * n
            if is_square(s_squared):
                s = int(math.sqrt(s_squared))
                fact_1 = (t - s) // k
                fact_2 = (t + s) // k
                return fact_1, fact_2

        k += 1


# Example usage: n = 141467 or 200819, B = 100
if __name__ == "__main__":
    # Replace these values with the number to factorize and a suitable bound B
    try:
        number_to_factorize = int(input("Enter an odd composite number (not a square): "))
        B = int(input("Enter a suitable bound B: "))
        factor_1, factor_2 = generalized_fermat_algorithm(number_to_factorize, B)
        print(f"Non-trivial factors of {number_to_factorize} are {factor_1} and {factor_2}")
    except ValueError as e:
        print(f"Error: {e}")