class Swap:
    def __init__(self, word_1, letter_1, word_2, letter_2, link):
        self.__word_1 = word_1
        self.__letter_1 = letter_1
        self.__word_2 = word_2
        self.__letter_2 = letter_2
        self.__link = link

    @property
    def word_1(self):
        return self.__word_1

    @property
    def letter_1(self):
        return self.__letter_1

    @property
    def word_2(self):
        return self.__word_2

    @property
    def letter_2(self):
        return self.__letter_2

    @property
    def link(self):
        return self.__link
