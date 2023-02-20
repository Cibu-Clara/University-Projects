from src.services.services import ComplexServices


def show_menu():
    print("1. Add complex numbers to the list")
    print("2. Display the entire list of numbers")
    print("3. Filter the list")
    print("4. Undo last operation")
    print("5. Exit")


class Console:
    def __init__(self):
        self._service = ComplexServices()

    def start(self):
        while True:
            show_menu()
            option = input("Enter an option")
            if option == '1':
                self.add_number_ui()
            elif option == '2':
                self.show_list()
            elif option == '3':
                self.filter_ui()
            elif option == '4':
                self._service.undo_last_command()
            elif option == '5':
                return
            else:
                print("Invalid option")

    def add_number_ui(self):
        try:
            n = int(input("Enter how many numbers to be added: "))
            for i in range(n):
                a = int(input("Enter real part: "))
                b = int(input("Enter imaginary part: "))
                self._service.add_number(a, b)
        except ValueError as ve:
            print(str(ve))

    def show_list(self):
        print_list = self._service.generate_list()
        index = 1
        for c in print_list:
            print('z' + str(index) + ' =', c)
            index += 1

    def filter_ui(self):
        try:
            start = int(input("Enter the start position: "))
            end = int(input("Enter the end position: "))
            self._service.filter_list(start, end)
        except IndexError as ie:
            print(str(ie))

