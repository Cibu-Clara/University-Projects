from Parser import Parser
from Grammar import Grammar
from ParserOutput import ParserOutput

def main():
    while True:
        print("Choose an option:")
        print("1. Run Parser 1")
        print("2. Run Parser 2")
        print("0. Quit")

        choice = input("Enter your choice: ")

        if choice == '1':
            sequence_file = "../input/seq1.txt"
            grammar_file = "../input/g1.txt"
        elif choice == '2':
            sequence_file = "../input/seq2.txt"
            grammar_file = "../input/g2.txt"
        elif choice == '0':
            break
        else:
            print("Invalid choice. Please select a valid option.")
            continue

        grammar = Grammar()
        grammar.read_from_file(grammar_file)

        parser = Parser(grammar, sequence_file)
        parser.run()

        parser_output = ParserOutput(grammar, sequence_file)
        parser_output.create_parsing_tree(parser.working)
        parser_output.write_parsing_tree(parser.state, parser.working, "../output/tree.txt")


if __name__ == "__main__":
    main()
