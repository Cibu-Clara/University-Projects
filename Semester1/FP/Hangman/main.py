from user_interface.ui import Console
from controller.service import HangmanServ
from repository.repo import HangmanRepo
from validation.validators import HangmanValidator

hangman_repo = HangmanRepo()

valid_hangman = HangmanValidator()

hangman_serv = HangmanServ(valid_hangman, hangman_repo)

ui = Console(hangman_serv)
ui.run()
