class Grammar:
    def __init__(self):
        # Initialize the Grammar object with empty lists and strings for non-terminals, terminals, starting symbol, and productions
        self.N = []  # non-terminals
        self.E = []  # terminals
        self.S = ""  # starting symbol/axiom
        self.P = {}  # finite set of productions

    def rebuild(self):
        # Clear/reset the grammar attributes
        self.N = []
        self.E = []
        self.S = ""
        self.P = {}

    @staticmethod
    def __process_line(line: str, delimiter=' '):
        # Static method to process a line and split it based on the given delimiter
        elements = line.strip().strip('{}').split(delimiter)
        if len(elements) > 1:
            elements[0] += delimiter
            elements[0:2] = [''.join(elements[0:2])]
        return [element.strip() for element in elements if element]

    def read_from_file(self, file_name: str):
        # Read grammar information from a file and populate the Grammar object
        self.rebuild()
        with open(file_name) as file:
            # Read non-terminals
            line = next(file)
            self.N = self.__process_line(line.split('=')[1], ', ')

            # Read terminals
            line = next(file)
            self.E = self.__process_line(line[line.find('=') + 1:-1].strip(), ', ')

            # Read starting symbol
            line = next(file)
            self.S = self.__process_line(line.split('=')[1], ', ')[0]

            # Skip lines until the productions start
            line = file.readline()
            while line.strip() and ' -> ' not in line:
                line = file.readline()

            # Read productions
            while line:
                if ' -> ' in line:
                    source, productions = line.split(" -> ")
                    source = source.strip()
                    for production in productions.split('|'):
                        production = production.strip().split()
                        if source in self.P:
                            self.P[source].append(production)
                        else:
                            self.P[source] = [production]
                line = file.readline()

    def check_cfg(self):
        # Check if the grammar is a valid context-free grammar
        has_starting_symbol = False
        for key in self.P.keys():
            if key == self.S:
                has_starting_symbol = True
            if key not in self.N[0].split():
                return False
        if not has_starting_symbol:
            return False
        for production in self.P.values():
            for rhs in production:
                for value in rhs:
                    if value not in self.N[0].split() and value not in self.E[0].split():
                        return False
        return True

    def get_non_terminals(self):
        return self.N

    def get_terminals(self):
        return self.E

    def get_start_symbol(self):
        return self.S

    def get_productions(self):
        return self.P

    def get_productions_for_non_terminal(self, nt):
        return self.P.get(nt, [])

    def __str__(self):
        # String representation of the Grammar object
        result = "N = " + str(self.N) + "\n"
        result += "E = " + str(self.E) + "\n"
        result += "S = " + str(self.S) + "\n"
        result += "P = " + str(self.P) + "\n"
        return result
