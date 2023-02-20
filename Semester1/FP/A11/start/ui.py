from src.game.game import Game, PlaneError, GameWonException
from src.game.computer_ai import CompMove


class Console:
    def __init__(self):
        self._game = Game()
        self._ai = CompMove(self._game.user_board)

    def start(self):
        print("Welcome to Planes! This is your board:")
        print(self._game.user_board)
        print("Please select the position for the cabins of your planes.")
        i = 1
        while i <= 3:
            try:
                print("Plane " + str(i) + ": \n    Coords:")
                x = input("    x (letter) = ")
                y = input("    y (digit) = ")
                dir = input("    direction (N/S/E/W) = ")
                if self._game.validation_plane(x, y, dir, 'user'):
                    print("This is your board now:")
                    print(self._game.user_board)
                    i = i + 1
                else:
                    pass
            except ValueError as ve:
                print(str(ve))
            except TypeError as te:
                print(str(te))
            except PlaneError as pe:
                print(str(pe))
        self._game.computer_place_plane()
        print("The computer has chosen the position of its 3 planes randomly.")
        player_turn = True
        computer_turn = False
        nr_hits_1 = 0
        nr_hits_2 = 0
        try:
            while True:
                try:
                    if player_turn:
                        print("Try hitting the computer's cabins!")
                        print(self._game.comp_hidden_board)
                        print("Enter coordinates:")
                        x = input("    x (letter) = ")
                        y = input("    y (digit) = ")
                        nr_hits_1 = self._game.player_move(x, y, nr_hits_1)
                        if self._game.game_is_won(nr_hits_1):
                            raise GameWonException("You won!")
                        player_turn = False
                        computer_turn = True
                    elif computer_turn:
                        x, y = self._ai.choose_coords()
                        nr_hits_2 = self._ai.computer_move(x, y, nr_hits_2)
                        print("Computer picked the cell " + chr(x + 64) + str(y) + " and hit " + str(nr_hits_2)
                              + " cabins")
                        if self._game.game_is_won(nr_hits_2):
                            raise GameWonException("Computer wins!")
                        computer_turn = False
                        player_turn = True
                except ValueError as ve:
                    print(str(ve))
                except TypeError as te:
                    print(str(te))
        except GameWonException as ge:
            print(str(ge))


ui = Console()
ui.start()
