from errors.exceptions import RepositoryError


class HangmanRepo:
    def __init__(self):
        self.__sentence_list = []

    def add_sentence(self, sentence):
        """
        Adds a new sentence to the list of sentences
        :param sentence: the sentence to be added
        :return: None
        """
        for s in self.sentence_list():
            if s.sentence == sentence.sentence:
                raise RepositoryError("Existing sentence!")
        self.__sentence_list.append(sentence)

    def update_hidden_sentence(self, updated_sentence):
        """
        Updates a sentence in the list of sentences
        :param updated_sentence: the updated sentence
        :return: None
        """
        for s in self.sentence_list():
            if s.sentence == updated_sentence.sentence:
                s.hidden_sentence = updated_sentence.hidden_sentence

    def sentence_list(self):
        return self.__sentence_list

    def __len__(self):
        return len(self.__sentence_list)

    def __getitem__(self, item):
        return self.__sentence_list[item]
