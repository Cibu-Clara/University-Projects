from domain.domain import Sentence


class HangmanServ:
    def __init__(self, valid_hangman, hangman_repo):
        self.__valid_hangman = valid_hangman
        self.__hangman_repo = hangman_repo

    def add_sentence(self, sentence, hidden_sentence):
        """
        Adds a new sentence to the list of sentences
        :param sentence: the new sentence
        :param hidden_sentence: the corresponding hidden sentence
        :return: None
        """
        new_sentence = Sentence(sentence, hidden_sentence)
        self.__valid_hangman.validate(new_sentence)
        self.__hangman_repo.add_sentence(new_sentence)

    @staticmethod
    def create_hidden_sentence(sentence):
        """
        Creates a hidden sentence in "hangman" style
        :param sentence: the sentences that needs to be codified
        :return: the codified sentence
        """
        words = sentence.split()
        sentence = ""
        used_chars = []
        for w in words:
            used_chars.append(w[0])
            used_chars.append(w[-1])
            chars = list(w)
            for c in chars:
                if c not in used_chars:
                    sentence += "_"
                else:
                    sentence += c
            sentence += " "
        return sentence

    @staticmethod
    def find_letter_in_sentence(letter, sentence):
        """
        :param letter: the letter to be searched
        :param sentence: the sentence in which we search the letter
        :return: True if letter is in sentence, False otherwise
        """
        if letter in sentence:
            return True
        return False

    @staticmethod
    def change_sentence(letter, sentence, hidden_sentence):
        """
        Adds the letter on the corresponding positions in the hidden sentence
        :param letter: the letter to be added
        :param sentence: the complete sentence
        :param hidden_sentence: the sentence in which we add the letters
        :return: the changed sentence
        """
        new_sentence = ""
        chars = list(sentence)
        chars_hidden = list(hidden_sentence)
        i = 0
        for c in chars:
            if c == letter:
                new_sentence += c
            else:
                new_sentence += chars_hidden[i]
            i += 1
        return new_sentence

    def update_hidden_sentence(self, sentence, hidden_sentence):
        """
        Updates the sentence in the list of sentences
        :param sentence: the complete sentence
        :param hidden_sentence: the hidden sentence
        :return: None
        """
        new_sentence = Sentence(sentence, hidden_sentence)
        self.__hangman_repo.update_hidden_sentence(new_sentence)

    def sentence_list(self):
        return self.__hangman_repo.sentence_list()

    def __len__(self):
        return len(self.__hangman_repo)

    def __getitem__(self, item):
        return self.__hangman_repo[item]
