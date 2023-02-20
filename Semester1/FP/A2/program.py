# UI section
def start():
    complex_numbers = generate_list()  # create a list of 10 complex numbers

    while True:
        print_menu()
        option = input("Enter option =")
        if option == '1':
            add_numbers_to_list(complex_numbers)
        elif option == '2':
            print_list(complex_numbers)
        elif option == '3':
            print_list(longest_sequence_distinct_numbers(complex_numbers))
        elif option == '4':
            print_list(longest_sequence_equal_sum(complex_numbers))
        elif option == '5':
            return
        else:
            print("Invalid option!")


def print_menu():
    print("1. Add complex numbers to the list")
    print("2. Display the entire list of numbers")
    print("3. Display the longest sequence of distinct numbers")
    print("4. Display the longest sequence of consecutive number pairs which have equal sum")
    print("5. Exit the application")


def add_numbers_to_list(complex_numbers):
    n = int(input("Enter how many numbers to be added"))
    for i in range(n):
        complex_numbers.append(read_number())


def read_number():
    a = int(input("Enter the real part"))
    b = int(input("Enter the imaginary part"))
    number = create_number(a, b)
    if number is None:
        print("Invalid complex number")
    else:
        return number


def print_list(complex_numbers):
    index = 1
    for i in complex_numbers:
        if get_imaginary_part(i) == 0:
            print('z' + str(index) + '=' + str(get_real_part(i)))
        elif get_real_part(i) == 0:
            if get_imaginary_part(i) == 1:
                print('z' + str(index) + '=' + 'i')
            elif get_imaginary_part(i) == -1:
                print('z' + str(index) + '=' + '-i')
            else:
                print('z' + str(index) + '=' + str(get_imaginary_part(i)) + 'i')
        elif get_imaginary_part(i) < 0:
            if get_imaginary_part(i) != - 1:
                print('z' + str(index) + '=' + str(get_real_part(i)) + str(get_imaginary_part(i)) + 'i')
            else:
                print('z' + str(index) + '=' + str(get_real_part(i)) + '-i')
        else:
            if get_imaginary_part(i) != 1:
                print('z' + str(index) + '=' + str(get_real_part(i)) + '+' + str(get_imaginary_part(i)) + 'i')
            else:
                print('z' + str(index) + '=' + str(get_real_part(i)) + '+i')
        index += 1


# Non-UI section
def create_number(r, i):
    return r, i  # real part, imaginary part


def generate_list():
    return [create_number(2, 5), create_number(3, -2), create_number(1, 3), create_number(2, 5), create_number(1, -1),
            create_number(2, 5), create_number(4, 2), create_number(-1, 1), create_number(3, 3), create_number(2, 0)]


def get_real_part(number):
    return number[0]  # accesses the real part


def get_imaginary_part(number):
    return number[1]  # accesses the imaginary part


def distinct_numbers(n1, n2):
    """
    Verifies if two complex numbers are distinct
    : param n1 : first number
    : param n2 : second number
    : return : the truth value
    """
    if get_real_part(n1) == get_real_part(n2) and get_imaginary_part(n1) == get_imaginary_part(n2):
        return False
    return True


def verify_distinct_numbers(begin, end, complex_numbers):
    """
    Goes through the list and verifies if all elements are distinct
    : param begin : start of the index
    : param end : finish of the index
    """
    for i in range(begin, end):
        for j in range(i + 1, end + 1):
            if not distinct_numbers(complex_numbers[i], complex_numbers[j]):
                return False
    return True


def longest_sequence_distinct_numbers(complex_numbers):
    """
    Finds the longest sublist which verifies the condition and then returns it by retaining the positions in the
    parameters 'first' and 'last'
    """
    max_value = 0
    first = 0
    last = 0
    for i in range(len(complex_numbers)):
        for j in range(len(complex_numbers) - 1, i, -1):
            if verify_distinct_numbers(i, j, complex_numbers):
                len_value = j - i + 1
                if len_value > max_value:
                    max_value = len_value
                    first = i
                    last = j
                    break
    return create_list(first, last, complex_numbers)


def create_list(first, last, complex_numbers):  # generates a sublist with the convenient elements of each property
    sequence = []
    for i in range(first, last + 1):
        sequence.append(complex_numbers[i])
    return sequence


def sum_of_two_elements(n1, n2):
    """
    Computes the sum of two complex numbers
    """
    sum_numbers = int(get_real_part(n1) + get_real_part(n2)), int(get_imaginary_part(n1) + get_imaginary_part(n2))
    return sum_numbers


def longest_sequence_equal_sum(complex_numbers):
    """
    Goes through the list and finds the longest sequence by incrementing the variable 'cnt' and comparing it with
    'max_value' at each step. After that, the positions are retained in the parameters 'first' and 'last' in order to
    create a list
    """
    max_value = 2
    s = sum_of_two_elements(complex_numbers[0], complex_numbers[1])
    cnt = 2
    i = 1
    first = 0
    while i < len(complex_numbers) - 1:
        current_sum = sum_of_two_elements(complex_numbers[i], complex_numbers[i + 1])
        if current_sum == s:
            cnt += 1
        else:
            if cnt > max_value:
                max_value = cnt
                first = i - cnt + 1
            cnt = 2
            s = current_sum
        i += 1
    if cnt > max_value:
        max_value = cnt
        first = i - cnt + 1
    last = first + max_value - 1
    return create_list(first, last, complex_numbers)


start()
