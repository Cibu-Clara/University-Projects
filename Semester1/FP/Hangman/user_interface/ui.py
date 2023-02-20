import random
from errors.exceptions import ValidationError, RepositoryError


class Console:
    def __init__(self, hangman_serv):
        self.__hangman_serv = hangman_serv

    def read_sentence_from_file(self):
        f = open("sentences.txt", "r")
        sentences = f.read().splitlines()
        for s in sentences:
            self.__hangman_serv.add_sentence(s, self.__hangman_serv.create_hidden_sentence(s))

    def add_sentence_ui(self):
        sentence = input("Enter sentence: ")
        f = open("sentences.txt", "a")
        self.__hangman_serv.add_sentence(sentence, self.__hangman_serv.create_hidden_sentence(sentence))
        f.write("\n")
        f.write(sentence)

    def update_sentence_ui(self, sentence, new_sentence):
        self.__hangman_serv.update_hidden_sentence(sentence, new_sentence)

    def run(self):
        self.read_sentence_from_file()
        while True:
            cmd = input("\n>>>")
            if cmd == "add":
                try:
                    self.add_sentence_ui()
                except ValidationError as ve:
                    print(str(ve))
                except RepositoryError as re:
                    print(str(re))
            elif cmd == "start":
                sentence = random.choice(self.__hangman_serv.sentence_list())
                hangman = ""
                hangman_chars = ["h", "a", "n", "g", "m", "a", "n"]
                choices = []
                i = 0
                while hangman != "hangman" and sentence.hidden_sentence != sentence.sentence:
                    print(sentence.hidden_sentence)
                    letter = input("User guess: ")
                    if letter in choices or not self.__hangman_serv.find_letter_in_sentence(letter, sentence.sentence):
                        hangman += hangman_chars[i]
                        i += 1
                    else:
                        new_sentence = self.__hangman_serv.change_sentence(letter, sentence.sentence,
                                                                           sentence.hidden_sentence)
                        self.update_sentence_ui(sentence.sentence, new_sentence)
                    choices.append(letter)
                    print(hangman)
                if hangman == "hangman":
                    print("Game over! You lost!")
                else:
                    print(sentence.hidden_sentence)
                    print("Congrats! You won!")
                return
            elif cmd == "exit":
                return
            else:
                print("Invalid command!")
