from errors.exceptions import ValidationError


class SwapValidator:
    @staticmethod
    def validate_swap(swap, sentence):
        words = sentence.split()
        first_word = int(swap.word_1)
        second_word = int(swap.word_2)
        first_letter = int(swap.letter_1)
        second_letter = int(swap.letter_2)
        errors = ""
        if swap.link != "-":
            errors += "Invalid linking!\n"
        if first_word < 0 or len(words) <= first_word:
            errors += "First word not in sentence!\n"
        elif first_letter < 0 or first_letter >= len(words[first_word]):
            errors += "First letter not in word!\n"
        elif first_letter == len(words[first_word]) - 1:
            errors += "You can't swap the last letter of a word!\n"
        if second_word < 0 or second_word >= len(words):
            errors += "Second word not in sentence!\n"
        elif second_letter < 0 or second_letter >= len(words[second_word]):
            errors += "Second letter not in word!\n"
        elif second_letter == len(words[second_word]) - 1:
            errors += "You can't swap the last letter of a word!\n"
        if first_letter == 0 or second_letter == 0:
            errors += "You can't swap the first letter of a word!\n"
        if len(errors) > 0:
            raise ValidationError(errors)
