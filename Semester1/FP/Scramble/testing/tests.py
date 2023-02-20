import unittest
from controller.service import ScrambleServ
from validation.validators import SwapValidator
from repository.repo import ScrambleRepo
from domain.domain import Swap


class ScrambleTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self.__scramble_service = ScrambleServ(SwapValidator, ScrambleRepo)
        self.__scramble_repo = ScrambleRepo()

    def test_no_first_and_last_letters(self):
        words = ["Clara", "Cibu"]
        new_words = self.__scramble_service.no_first_and_last_letters(words)
        self.assertEqual(new_words, ["lar", "ib"])

    def test_shuffle(self):
        words = ["Clara", "Cibu"]
        chars = self.__scramble_service.shuffle(words)
        self.assertEqual(len(chars), 5)

    def test_scrambled_sentence(self):
        words = ["Clara", "Cibu"]
        sentence = self.__scramble_service.scrambled_sentence(words)
        self.assertEqual(sentence[0], "C")
        self.assertEqual(sentence[4], "a")
        self.assertEqual(sentence[6], "C")
        self.assertEqual(sentence[9], "u")
        initial = ""
        for w in words:
            initial += w + " "
        initial = initial.strip()
        self.assertNotEqual(initial, sentence)

    def test_get_score(self):
        sentence = "Practical exam"
        score = self.__scramble_service.get_score(sentence)
        self.assertEqual(score, 13)

    def test_add_swap(self):
        self.__scramble_repo.add_swap(Swap(0, 1, 2, 1, '-'))
        swap = self.__scramble_repo.swap_list()[-1]
        self.assertEqual(swap.word_1, 0)
        self.assertEqual(swap.letter_1, 1)
        self.assertEqual(swap.word_2, 2)
        self.assertEqual(swap.letter_2, 1)
        self.assertEqual(swap.link, '-')

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
