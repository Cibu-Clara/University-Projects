import random
import unittest

from src.domain.board import Board
from src.domain.plane import Plane


class Game:
    def __init__(self):
        self._user_board = Board(11, 11)
        self._computer_board = Board(11, 11)
        self._computer_hidden_board = Board(11, 11)

    @property
    def user_board(self):
        """
        :return: the user's board
        """
        return self._user_board

    @property
    def comp_board(self):
        """
        :return: the computer's board
        """
        return self._computer_board

    @property
    def comp_hidden_board(self):
        """
        :return: the computer's hidden board
        """
        return self._computer_hidden_board

    @staticmethod
    def direction(x, y, dir):
        """
        :param x: the coordinate of the row
        :param y: the coordinate of the column
        :param dir: the cardinal point of the direction
        :return: the list of cells where the plane with the cabin at (x, y) is placed
        """
        if dir == 'N':
            neighbors = [(x + 1, y), (x + 2, y), (x + 3, y), (x + 1, y - 1), (x + 1, y - 2), (x + 1, y + 1),
                         (x + 1, y + 2), (x + 3, y - 1), (x + 3, y + 1)]
        elif dir == 'S':
            neighbors = [(x - 1, y), (x - 2, y), (x - 3, y), (x - 1, y - 1), (x - 1, y - 2), (x - 1, y + 1),
                         (x - 1, y + 2), (x - 3, y - 1), (x - 3, y + 1)]
        elif dir == 'E':
            neighbors = [(x, y - 1), (x, y - 2), (x, y - 3), (x - 1, y - 1), (x - 2, y - 1), (x + 1, y - 1),
                         (x + 2, y - 1), (x - 1, y - 3), (x + 1, y - 3)]
        else:
            neighbors = [(x, y + 1), (x, y + 2), (x, y + 3), (x - 1, y + 1), (x - 2, y + 1), (x + 1, y + 1),
                         (x + 2, y + 1), (x - 1, y + 3), (x + 1, y + 3)]
        return neighbors

    def place_plane(self, board, x, y, dir):
        """
        :param board: the board called in validation_plane (computer/ user)
        :param x: the coordinate of the row
        :param y: the coordinate of the column
        :param dir: the cardinal point of the direction
        :return: None
        """
        x = int(x)
        y = int(y)
        neighbors = self.direction(x, y, dir)
        for n in neighbors:
            if not (1 <= n[0] <= board.rows - 1 and 1 <= n[1] <= board.cols - 1):
                raise PlaneError("Plane is outside the board. Enter another position")
            elif board[n[0]][n[1]].is_plane or board[n[0]][n[1]].is_cabin:
                raise PlaneError("Plane collides with another plane. Enter another position")

        board.plane_list().append(Plane(x, y, dir))
        board[x][y].empty = False
        board[x][y].is_cabin = True
        for n in neighbors:
            board[n[0]][n[1]].empty = False
            board[n[0]][n[1]].is_plane = True

    def validation_plane(self, coord_x, coord_y, direction, player):
        """
        :param coord_x: the coordinate of the row
        :param coord_y: the coordinate of the column
        :param direction: the cardinal point of the direction
        :param player: user/ computer
        :return: True if plane coordinates are valid
        """
        if player == 'computer':
            board = self.comp_board
        else:
            board = self.user_board
        if not coord_x.isalpha() or not str(coord_y).isdigit() or not direction.isalpha():
            raise TypeError("Please select a valid entry")
        coord_x = coord_x.upper()
        if coord_x not in ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]:
            raise ValueError("x must be a letter between A and " + chr(board.rows + 63))
        if int(coord_y) <= 0 or int(coord_y) >= int(board.cols):
            raise ValueError("y must be a value between 1 and " + str(board.cols - 1))
        direction = direction.upper()
        if direction not in ["N", "S", "E", "W"]:
            raise ValueError("direction must be a cardinal point")
        coord_x = ord(coord_x) - 64
        if board[int(coord_x)][int(coord_y)].is_plane or board[int(coord_x)][int(coord_y)].is_cabin:
            raise PlaneError("Plane collides with another plane. Enter another position")
        else:
            self.place_plane(board, coord_x, coord_y, direction)
        return True

    def computer_place_plane(self):
        """
        Picks randomly the positions of the computer's cabins and checks if they are valid
        :return: None
        """
        my_list = []
        for i in range(1, self.comp_board.rows):
            my_list.append(chr(i + 64))
        i = 1
        while i <= 3:
            try:
                x = random.choice(my_list)
                y = random.randint(1, self.comp_board.cols - 1)
                dir = random.choice(["N", "S", "E", "W"])
                if self.validation_plane(x, y, dir, 'computer'):
                    i = i + 1
                else:
                    pass
            except PlaneError:
                pass

    @staticmethod
    def game_is_won(nr_hits):
        """
        :param nr_hits: the number of the cabins in the computer's board that are hit
        :return: True if the game is won, False otherwise
        """
        if nr_hits == 3:
            return True
        return False

    def player_move(self, x, y, nr_hits):
        """
        :param x: the coordinate of the row
        :param y: the coordinate of the column
        :param nr_hits: the number of the cabins in the computer's board that are hit
        :return: the number of the cabins that are hit after the player's move
        """
        board = self.comp_board
        hidden_board = self.comp_hidden_board
        if not x.isalpha() or not str(y).isdigit():
            raise TypeError("Please enter valid coordinates")
        x = x.upper()
        if x not in ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]:
            raise ValueError("x must be a letter between A and " + chr(board.rows + 63))
        if int(y) <= 0 or int(y) >= int(board.cols):
            raise ValueError("y must be a value between 1 and " + str(board.cols - 1))
        x = ord(x) - 64
        if not hidden_board[int(x)][int(y)].empty:
            raise ValueError("You already hit this cell")
        elif board[int(x)][int(y)].is_cabin:
            nr_hits += 1
            for p in board.plane_list():
                if int(p.row) == int(x) and int(p.col) == int(y):
                    dir = p.dir
                    neighbors = self.direction(int(x), int(y), dir)
                    for n in neighbors:
                        hidden_board[n[0]][n[1]].empty = False
                        hidden_board[n[0]][n[1]].is_plane = True
            hidden_board[int(x)][int(y)].empty = False
            hidden_board[int(x)][int(y)].is_cabin = True

        elif board[int(x)][int(y)].is_plane:
            hidden_board[int(x)][int(y)].empty = False
            hidden_board[int(x)][int(y)].is_plane = True
        else:
            hidden_board[int(x)][int(y)].empty = False
        return nr_hits


class PlaneError(Exception):
    pass


class GameWonException(Exception):
    pass


class GameTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._game = Game()

    def test_game_is_won(self):
        nr_hits = 3
        ok = self._game.game_is_won(nr_hits)
        self.assertTrue(ok)
        nr_hits = 1
        ok = self._game.game_is_won(nr_hits)
        self.assertFalse(ok)

    def test_direction(self):
        neighbors = self._game.direction(3, 5, 'N')
        my_list = [(4, 5), (5, 5), (6, 5), (4, 4), (4, 3), (4, 6), (4, 7), (6, 4), (6, 6)]
        self.assertListEqual(neighbors, my_list)

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
