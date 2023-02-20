class Sentence:
    def __init__(self, sentence, hidden_sentence):
        self.__sentence = sentence
        self.__hidden_sentence = hidden_sentence

    @property
    def sentence(self):
        return self.__sentence

    @property
    def hidden_sentence(self):
        return self.__hidden_sentence

    @hidden_sentence.setter
    def hidden_sentence(self, new):
        self.__hidden_sentence = new

    def __str__(self):
        return str(self.hidden_sentence)
