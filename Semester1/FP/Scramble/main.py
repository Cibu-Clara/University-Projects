from user_interface.ui import Console
from controller.service import ScrambleServ
from repository.repo import ScrambleRepo
from validation.validators import SwapValidator

scramble_repo = ScrambleRepo()

valid_scramble = SwapValidator()

scramble_service = ScrambleServ(valid_scramble, scramble_repo)

ui = Console(scramble_service)
ui.run()
