import random


def generate_key():
    p = 31
    q = 51
    n = p * q
    public_key = n
    private_key = (p, q)
    return public_key, private_key


def generate_prime():
    while True:
        num = random.randint(100, 1000)
        if is_prime(num):
            return num


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


def split_into_blocks(text, k):
    blocks = []
    for i in range(0, len(text), k):
        blocks.append(text[i:i + k])
    if len(blocks[-1]) < k:
        blocks[-1] += ' ' * (k - len(blocks[-1]))
    return blocks


def text_to_numbers(blocks, alphabet, k):
    numbers = []
    for block in blocks:
        number = 0
        for index, symbol in enumerate(block):
            number += alphabet.index(symbol) * (27 ** (k - index - 1))
        numbers.append(number)
    return numbers


def to_blocks(numbers, alphabet, l):
    blocks = []
    for number in numbers:
        block = ''
        for i in range(l):
            block = alphabet[number % 27] + block
            number //= 27
        blocks.append(block)
    return blocks


def numbers_to_text(num, l, alphabet):
    block = ''
    for _ in range(l):
        char_index = num % 27
        block = alphabet[char_index] + block
        num //= 27
    return block


def blocks_to_text(blocks):
    text = ''
    for block in blocks:
        text += block
    return text


def extended_gcd(a, b):
    if a == 0:
        return b, 0, 1
    else:
        gcd, x, y = extended_gcd(b % a, a)
        return gcd, y - (b // a) * x, x


def mod_inverse(a, m):
    gcd, x, _ = extended_gcd(a, m)
    if gcd != 1:
        raise ValueError("Modular inverse does not exist")
    else:
        return x % m


def modular_square_roots(c, p, q, n):
    m_p = pow(c, (p + 1) // 4, p)
    m_q = pow(c, (q + 1) // 4, q)

    # Calculate the modular inverse of q modulo p and p modulo q
    inv_q = mod_inverse(q, p)
    inv_p = mod_inverse(p, q)

    # Apply Chinese Remainder Theorem (CRT) to obtain roots
    x1 = (m_p * q * inv_q + m_q * p * inv_p) % n
    x2 = n - x1
    x3 = (m_p * q * inv_q + (n - m_q) * p * inv_p) % n
    x4 = n - x3

    return x1, x2, x3, x4


def encrypt(alphabet, plaintext, k, l, public_key):
    n = public_key
    blocks = split_into_blocks(plaintext, k)
    print(blocks)
    numbers = text_to_numbers(blocks, alphabet, k)
    print(numbers)
    ciphertext_numbers = []

    for num in numbers:
        ciphertext_block = pow(num, 2, n)
        ciphertext_numbers.append(ciphertext_block)

    blocks = to_blocks(ciphertext_numbers, alphabet, l)
    return blocks_to_text(blocks)


def decrypt(alphabet, ciphertext, l, k, private_key, public_key):
    p, q = private_key
    n = public_key

    blocks = split_into_blocks(ciphertext, l)
    print(blocks)
    numbers = text_to_numbers(blocks, alphabet, l)
    print(numbers)

    decrypted_messages = []

    for i in numbers:
        m1, m2, m3, m4 = modular_square_roots(i, p, q, n)
        valid_messages = []

        for root in [m1, m2, m3, m4]:
            print(root)
            if 0 <= root < n:  # Check if root is within the range of the original message block
                valid_messages.append(numbers_to_text(root, k, alphabet))

        decrypted_messages.append(valid_messages)

    return decrypted_messages


def main():
    print("Rabin Cipher Implementation")

    # (i) Setting
    alphabet = ' abcdefghijklmnopqrstuvwxyz'
    k = 2  # Block size for plaintext
    l = 3  # Block size for ciphertext

    # (ii) Generate keys
    public_key, private_key = generate_key()
    print(f"Public Key: {public_key}")
    print(f"Private Key: {private_key}")

    while True:
        print("\nOptions:")
        print("1. Encrypt")
        print("2. Decrypt")
        print("3. Exit")

        choice = input("Enter your choice (1, 2, or 3): ")

        # (iii) Encrypt
        if choice == '1':
            plaintext = input("Enter the plaintext: ")
            ciphertext = encrypt(alphabet, plaintext, k, l, public_key)
            print(f"Encrypted Text: {ciphertext}")

        # (iv) Decrypt
        elif choice == '2':
            ciphertext = input("Enter the ciphertext: ")
            decrypted_messages = decrypt(alphabet, ciphertext, l, k, private_key, public_key)
            print(f"Decrypted Messages: {decrypted_messages}")

        elif choice == '3':
            print("Exiting program...")
            break
        else:
            print("Invalid choice. Please enter 1, 2, or 3.")


if __name__ == "__main__":
    main()