import unittest
from repository.repo import HangmanRepo
from validation.validators import HangmanValidator
from controller.service import HangmanServ
from domain.domain import Sentence


class HangmanTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self.__hangman_repo = HangmanRepo()
        self.__hangman_serv = HangmanServ(HangmanValidator, HangmanRepo)

    def test_create_hidden_sentence(self):
        new_sentence = self.__hangman_serv.create_hidden_sentence("clara")
        self.assertEqual(new_sentence, "c_a_a ")

    def test_find_letter_in_sentence(self):
        self.assertTrue(self.__hangman_serv.find_letter_in_sentence("a", "clara"))

    def test_change_sentence(self):
        new_sentence = self.__hangman_serv.change_sentence("r", "clara", "c_a_a")
        self.assertEqual(new_sentence, "c_ara")

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
