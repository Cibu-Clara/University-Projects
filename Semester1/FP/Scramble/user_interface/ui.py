import random
from errors.exceptions import ValidationError


class Console:
    def __init__(self, scramble_serv):
        self.__scramble_serv = scramble_serv

    @staticmethod
    def read_sentences():
        f = open("input.txt", "r")
        return f.read().splitlines()

    def run(self):
        sentence_list = self.read_sentences()
        sentence = random.choice(sentence_list)
        words = sentence.split()
        scrambled_sentence = self.__scramble_serv.scrambled_sentence(words).strip()
        score = self.__scramble_serv.get_score(scrambled_sentence)
        while score > 0 and sentence != scrambled_sentence:
            print(scrambled_sentence)
            print("Score is:", score)
            try:
                cmd = input(">>>")
                params = cmd.split()
                if len(params) == 0:
                    print("Invalid command!\n")
                else:
                    cmd_word = params[0]
                    if cmd_word == "swap":
                        word_1 = params[1]
                        letter_1 = params[2]
                        link = params[3]
                        word_2 = params[4]
                        letter_2 = params[5]
                        self.__scramble_serv.add_swap(word_1, letter_1, word_2, letter_2, link, scrambled_sentence)
                        scrambled_sentence = self.__scramble_serv.swapping(scrambled_sentence)
                        score -= 1
                    elif cmd_word == "undo" and len(params) == 1:
                        scrambled_sentence = self.__scramble_serv.undo(scrambled_sentence)
                    elif cmd_word == "exit" and len(params) == 1:
                        return
                    else:
                        print("Invalid command!\n")
            except ValidationError as ve:
                print(str(ve))
            except IndexError:
                print("Not enough parameters!\n")
            except ValueError:
                print("The indices of the two words and letters must be integers!\n")
        print(scrambled_sentence)
        if score == 0:
            print("Game over! You lost!")
        else:
            print("Congrats! You won!")
