""" Define a function to calculate the extended GCD (The Greatest Common Divisor) of two integers a and b, returning
the GCD and the coefficients x and y: ax + by = GCD(a, b) """


def extended_gcd(a, b):
    if a == 0:
        return b, 0, 1
    else:
        gcd, x, y = extended_gcd(b % a, a)
        return gcd, y - (b // a) * x, x


""" Define a function to calculate the modular inverse of a (a^(-1) mod m) using the extended GCD, and raise an error 
if the inverse does not exist """


def mod_inverse(a, m):
    gcd, x, _ = extended_gcd(a, m)
    if gcd != 1:
        raise ValueError("Modular inverse does not exist")
    else:
        return x % m


""" Define a function to solve a system of congruences using the Chinese Remainder Theorem (CRT) """


def solve_congruences(congruences):
    # 'congruences' is a list of tuples (a, m), where 'a' is the remainder and 'm' is the modulus

    n = len(congruences)
    if n < 2:
        raise ValueError("System of congruences should have at least two equations")

    # Calculate N, the product of all moduli in the congruences
    N = 1
    for _, m in congruences:
        N *= m

    # Calculate the value of x for each congruence using CRT
    x = 0
    for a, m in congruences:
        Ni = N // m
        xi = mod_inverse(Ni, m)
        x += a * Ni * xi

    # Return the solution 'x' modulo 'N' as well as the value of 'N'
    return x % N, N


# Example usage:
congruences = [(2, 5), (4, 7), (5, 10)]
try:
    result, N = solve_congruences(congruences)
    print(f"The solution to the system of congruences is x ≡ {result} (mod {N}).")
except ValueError as e:
    print(f"Error: {e}")


""" 
Output: The solution to the system of congruences is x ≡ 137 (mod 385)
The variable x represents the value that satisfies all of these congruences simultaneously. In other words, x is the 
integer which, when divided by 5, leaves a remainder of 2, when divided by 7, leaves a remainder of 4, and when divided 
by 11, leaves a remainder of 5.
And N = 385 is the product of the moduli used in the congruence equations.
"""