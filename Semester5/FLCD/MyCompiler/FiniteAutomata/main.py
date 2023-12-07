from FA import FA


def menu(automata):
    while True:
        inp = input(">")
        if inp == "1":
            print(automata.states)
        elif inp == "2":
            print(automata.alphabet)
        elif inp == "3":
            print(automata.transition)
        elif inp == "4":
            print(automata.initial_state)
        elif inp == "5":
            print(automata.final_states)
        elif inp == "6":
            print(automata)
        elif inp == "7":
            print(check_sequence(automata))
        elif inp == "0":
            break
        else:
            print("Invalid action!")


def check_sequence(automata):
    inp = input("Please enter the sequence\n>").strip().replace(" ", "").split(",")
    return automata.check_sequence(inp)


def print_menu():
    print("1. Print the set of states\n"
          "2. Print the alphabet\n"
          "3. Print the transition functions\n"
          "4. Print the initial state\n"
          "5. Print the set of final states\n"
          "6. Print everything\n"
          "7. Check a sequence\n"
          "0. Exit\n\n")


if __name__ == "__main__":
    fa = FA("../input/FA.in")
    print(f"Finite automata is deterministic: {fa.deterministic()}")
    print_menu()
    menu(fa)
