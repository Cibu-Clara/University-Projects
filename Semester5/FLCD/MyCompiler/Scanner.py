import re

from SymbolTable import SymbolTable
from FiniteAutomata.FA import FA


class Scanner:
    def __init__(self, program_file):
        self.__symTable = SymbolTable()
        self.__tokens = []
        self.__program_file = program_file
        self.__program = None
        self.__pif = []
        self.__line_cnt = 0
        self.__fa = FA("input/FA.in")

        try:
            self.read_tokens()
            self.read_program()
            self.scan()
        except ValueError as err:
            print(err)

    def read_tokens(self):
        with open("input/tokens.in", "r") as tokens_file:
            self.__tokens = [line.strip() for line in tokens_file.readlines()]

    def read_program(self):
        with open(self.__program_file, "r") as program_file:
            self.__program = program_file.readlines()

    def write_to_file(self):
        with open("output/PIF.out",  "w") as pif_file:
            for el in self.__pif:
                pif_file.write(str(el) + "\n")

        with open("output/ST.out", "w") as st_file:
            st_file.write(self.__symTable.__str__())

    def scan(self):
        for code_line in self.__program:
            line_tokens = self.tokenize(code_line)

            for token in line_tokens:
                if token in self.__tokens:
                    self.__pif.append((token, -1))
                else:
                    if self.token_type(token) == 0:
                        raise ValueError("Lexical error: Token {} cannot be classified: line {}".format(token, self.__line_cnt))
                    if self.token_type(token) == "identifier":
                        self.__symTable.add(token)
                        self.__pif.append(('identifier', (self.__symTable.get_position(token))))
                    if self.token_type(token) == "string" or self.token_type(token) == "character" or self.token_type(token) == "number":
                        self.__symTable.add(token)
                        self.__pif.append(('constant', (self.__symTable.get_position(token))))

        self.write_to_file()
        print("Lexically correct!")

    def tokenize(self, code_line):
        self.__line_cnt += 1
        """
        1. A sequence of characters enclosed in double quotation marks.
        2. Alphanumeric sequences (letters or digits), including underscore.
        3. Sequences of characters that are not alphanumeric, double quotes, spaces, semicolon, colon or parentheses.
        4. Opening parenthesis.
        5. Closing parenthesis.
        6. Semicolon.
        7. Colon.
        """
        line_elems = re.findall(r'("[^"]+"|[a-zA-Z_0-9]+|[^a-zA-Z0-9"\s;:()]+|\(|\)|;|:)', code_line)
        # print(line_elems)
        return line_elems

    def token_type(self, token):
        if self.__fa.is_identifier(token):
            return "identifier"
        string_match = re.match('"[^"]', token)
        if string_match is not None:
            return "string"
        else:
            char_match = re.match('^\'[.\'$]', token)
            if char_match is not None:
                return "character"
            else:
                if self.__fa.is_integer_constant(token):
                    return "number"
        return 0


def test_p1():
    scanner = Scanner("input/p1.txt")


def test_p2():
    scanner = Scanner("input/p2.txt")


def test_p3():
    scanner = Scanner("input/p3.txt")


def test_p1err():
    scanner = Scanner("input/p1err.txt")


test_p1()
