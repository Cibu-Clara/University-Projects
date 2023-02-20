from errors.exceptions import ValidationError


class HangmanValidator:
    @staticmethod
    def validate(sentence):
        sentence = sentence.sentence
        if sentence == "":
            raise ValidationError("Sentence must contain at least one word!")
        words = sentence.split()
        for w in words:
            if len(w) < 3:
                raise ValidationError("Words must have at least 3 letters!")
