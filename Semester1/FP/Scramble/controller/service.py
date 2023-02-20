import random
from domain.domain import Swap
from errors.exceptions import ValidationError


class ScrambleServ:
    def __init__(self, valid_scramble, scramble_repo):
        self.__valid_scramble = valid_scramble
        self.__scramble_repo = scramble_repo

    def add_swap(self, word_1, letter_1, word_2, letter_2, link, sentence):
        """
        Creates a new swap, validates it and adds it to the repo
        :param word_1: first chosen word
        :param letter_1: first letter to be swapped
        :param word_2: second chosen word
        :param letter_2: second letter to be swapped
        :param link: must be a hyphen
        :param sentence: the scrambled sentence
        :return: None
        """
        swap = (Swap(word_1, letter_1, word_2, letter_2, link))
        self.__valid_scramble.validate_swap(swap, sentence)
        self.__scramble_repo.add_swap(swap)

    @staticmethod
    def no_first_and_last_letters(words):
        """
        :param words: a list containing the words of the initial sentence
        :return: the list of words without their first and last letters
        """
        new_list = []
        for w in words:
            get_rid = w[1: -1]
            new_list.append(get_rid)
        return new_list

    def shuffle(self, words):
        """
        Takes a list of words, transforms it into a string and then takes the list of its characters in order to shuffle
        it
        :param words: a list containing the words of the initial sentence without their first and last letters
        :return: the list of characters of the words
        """
        new_words = self.no_first_and_last_letters(words)
        string = ''.join(new_words)
        chars = list(string)
        random.shuffle(chars)
        return chars

    def scrambled_sentence(self, words):
        """
        Takes a list of words of a sentence and then recreates the sentence with shuffled letters, keeping only the
        first and last letter from each word
        :param words: a list containing the words of the initial sentence
        :return: the scrambled sentence
        """
        chars = self.shuffle(words)
        sentence = ""
        for w in words:
            first = w[0]
            last = w[-1]
            nr = len(w) - 2
            sentence += first
            for c in range(nr):
                sentence += chars[c]
            sentence += last + " "
            del(chars[0: nr])
        return sentence

    @staticmethod
    def get_score(sentence):
        """
        :param sentence: the scrambled sentence
        :return: the score
        """
        sentence = sentence.replace(" ", "")
        return len(sentence)

    def swapping(self, sentence):
        """
        Does the swap
        :param sentence: the scrambled sentence
        :return: the scrambled sentence with swapped letters
        """
        swap = self.swap_list()[-1]
        first_letter = int(swap.letter_1)
        second_letter = int(swap.letter_2)
        words = sentence.split()
        first_word = list(words[int(swap.word_1)])
        second_word = list(words[int(swap.word_2)])
        if first_word == second_word:
            first_word[first_letter], first_word[second_letter] = first_word[second_letter], first_word[first_letter]
            words[int(swap.word_1)] = ''.join(first_word)
        else:
            first_word[first_letter], second_word[second_letter] = second_word[second_letter], first_word[first_letter]
            words[int(swap.word_1)] = ''.join(first_word)
            words[int(swap.word_2)] = ''.join(second_word)
        sentence = ""
        for w in words:
            sentence += w + " "
        sentence = sentence.strip()
        return sentence

    def undo(self, sentence):
        if len(self.swap_list()) == 0:
            raise ValidationError("Nothing to undo!\n")
        else:
            swap = self.swap_list()[-1]
            self.swap_list().pop()
            self.add_swap(swap.word_1, swap.letter_1, swap.word_2, swap.letter_2, swap.link, sentence)
            sentence = self.swapping(sentence)
            self.swap_list().pop()
            return sentence

    def swap_list(self):
        return self.__scramble_repo.swap_list()

    def __len__(self):
        return len(self.__scramble_repo)

    def __getitem__(self, item):
        return self.__scramble_repo[item]
