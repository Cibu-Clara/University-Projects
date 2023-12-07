class FA:
    def __init__(self, filename):
        self.states = []
        self.alphabet = []
        self.transition = {}
        self.initial_state = ""
        self.final_states = []
        self.read_file(filename)

    def read_file(self, filename):
        with open(filename) as f:

            # split STATES by ",", remove unnecessary spaces   -> p,q,r
            self.states = f.readline().strip().replace(" ", "").split(",")
            if len(self.states) == 0:
                raise RuntimeError("Error while parsing states")

            # split ALPHABET by ",", remove unnecessary spaces -> 0,1
            self.alphabet = f.readline().strip().replace(" ", "").split(",")
            if len(self.alphabet) == 0:
                raise RuntimeError("Error while parsing alphabet")

            # INITIAL STATE is simply in the next line -> p
            self.initial_state = f.readline().strip()
            if len(self.initial_state) == 0:
                raise RuntimeError("Error while parsing initial state")

            # FINAL STATES split by " " , remove unnecessary spaces
            # ONLY ONE FINAL STATE -> r
            self.final_states = f.readline().strip().replace(" ", "").split(",")
            if len(self.final_states) == 0:
                raise RuntimeError("Error while parsing final states")

            # TRANSITIONS, split by "=" , remove unnecessary spaces
            for read_line in f:
                line = read_line.strip().replace(" ", "").split("=")
                pair = line[0].strip().split(",")
                if len(pair) != 2 or len(line) == 0:
                    raise RuntimeError("Error while parsing transition functions")
                self.transition.setdefault((pair[0], pair[1]), []).append(line[1])
                # {('p', '0'): ['q'], ('q', '0'): ['q'], ('q', '1'): ['r']}

    def deterministic(self):
        return False if any([elem for elem in self.transition.values() if len(elem) > 1]) else True

    def check_sequence(self, sequence):
        if self.deterministic():
            state = self.initial_state
            for symbol in sequence:
                transition_key = (state, symbol)
                # print(f"Transition key: {transition_key}")
                if transition_key not in self.transition.keys():
                    # print("No transition defined for key.")
                    return False
                next_states = self.transition[transition_key]
                # print(f"Next states: {next_states}")
                state = next_states[0]
            return state in self.final_states
        return False

    def is_identifier(self, token):
        return self.check_sequence(token) and 'q1' in self.final_states

    def is_integer_constant(self, token):
        return self.check_sequence(token) and ('q2' in self.final_states or 'q3' in self.final_states)

    def __repr__(self):
        return " States: " + str(self.states) + "\n Alphabet: " + str(
            self.alphabet) + "\n Transition Functions: " + str(
            self.transition) + "\n Initial state: " + self.initial_state + "\n Final states: " + str(self.final_states)


# fa = FA("../input/FA.in")
#
#
# identifiers = ["variable", "0variable", "Invalid123", "_underscore"]
# integer_constants = ["123", "07", "Invalid123", "1a", "0"]
#
# print("\nIdentifiers:")
# for identifier in identifiers:
#     print(f"{identifier}: {fa.check_sequence(identifier)}")
#
# print("\nInteger Constants:")
# for integer_constant in integer_constants:
#     print(f"{integer_constant}: {fa.check_sequence(integer_constant)}")
