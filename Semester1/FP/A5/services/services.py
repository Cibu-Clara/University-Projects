from src.domain.complex import Complex
import copy


class ComplexServices:
    def __init__(self):
        self._stack = [[]]
        self._complex_numbers = []
        ComplexServices.start_up(self)

    def add_number(self, a, b):
        """
        :param a: the real part of the number we add
        :param b: the imaginary part of the number we add
        :return : list of complex numbers
        """
        self._complex_numbers.append(Complex(a, b))
        self.save_stack()

        return self._complex_numbers

    def start_up(self):
        """
        Generate 10 entries at start-up
        """
        self.add_number(2, -1)
        self.add_number(-3, 5)
        self.add_number(4, 2)
        self.add_number(-1, 1)
        self.add_number(6, -3)
        self.add_number(-2, 0)
        self.add_number(0, 1)
        self.add_number(3, 3)
        self.add_number(-1, 0)
        self.add_number(9, -2)

    def generate_list(self):  # generates the list of complex numbers
        return self._complex_numbers

    def filter_list(self, start, end):
        """
        :param start: first position for filter functionality
        :param end: last position for filter functionality

        Creates a new list containing the filtered values
        """
        new_list = []
        if start > end:
            raise IndexError("Start position must be before end position")
        index = 1
        for c in self._complex_numbers:
            if start <= index <= end:
                new_list.append(c)
            index += 1
        self._complex_numbers = new_list
        self.save_stack()

    def undo_last_command(self):
        if len(self._stack) != 1:
            self._complex_numbers[:] = copy.deepcopy(self._stack[-2])
            self._stack.pop()

    def save_stack(self):
        """
        Saves the list of complex numbers in stack every time before certain commands, so that we can undo that command
        """
        self._stack.append(self._complex_numbers)
        self._stack[-1] = copy.deepcopy(self._complex_numbers)


def test_add_number():
    obj = ComplexServices()
    test_list = obj.add_number(2, -2)
    assert test_list[-1].get_a == 2
    assert test_list[-1].get_b == -2


test_add_number()
