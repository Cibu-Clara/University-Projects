class Complex:
    def __init__(self, a, b):
        """
        :param a: real part of a complex number
        :param b: imaginary part of a complex number
        """
        if not isinstance(a, int):
            raise ValueError("a must be an integer")
        if not isinstance(b, int):
            raise ValueError("b must be an integer")
        self.__complex_a = a
        self.__complex_b = b

    @property
    def get_a(self):
        return self.__complex_a

    @property
    def get_b(self):
        return self.__complex_b

    def set_a(self, a):
        if not isinstance(a, int):
            raise ValueError("a must be an integer")
        self.__complex_a = a

    def set_b(self, b):
        if not isinstance(b, int):
            raise ValueError("b must be an integer")
        self.__complex_b = b

    def __str__(self):
        if self.get_b == 0:
            return str(self.get_a)
        elif self.get_a == 0:
            if self.get_b == 1:
                return 'i'
            elif self.get_b == -1:
                return '-i'
            else:
                return str(self.get_b) + 'i'
        elif self.get_b < 0:
            if self.get_b != - 1:
                return str(self.get_a) + str(self.get_b) + 'i'
            else:
                return str(self.get_a) + '-i'
        else:
            if self.get_b != 1:
                return str(self.get_a) + '+' + str(self.get_b) + 'i'
            else:
                return str(self.get_a) + '+i'
