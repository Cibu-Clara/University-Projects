import random
import unittest

from src.domain.board import Board
from src.game.game import Game


class CompMove:
    def __init__(self, user_board):
        self._user_hidden_board = Board(11, 11)
        self._game = Game()
        self._user_board = user_board

    @property
    def user_hidden_board(self):
        """
        :return: the user's hidden board
        """
        return self._user_hidden_board

    @property
    def user_board(self):
        """
        :return: the user's board
        """
        return self._user_board

    def choose_coords(self):
        """
        :return: the chosen coordinates for the computer's turn
        """
        board = self.user_board
        coords = []
        x = random.randint(1, board.rows - 1)
        y = random.randint(1, board.cols - 1)
        if board[x][y].is_plane:
            for i in range(x - 3, x + 4):
                for j in range(y - 3, y + 4):
                    coords.append((i, j))
            x = random.choice(coords[0])
            y = random.choice(coords[1])
        return x, y

    def computer_move(self, x, y, nr_hits):
        """
        :param x: the coordinate of the row
        :param y: the coordinate of the column
        :param nr_hits: the number of the cabins in the user's board that are hit
        :return: the number of the cabins that are hit after the computer's move
        """
        board = self.user_board
        hidden_board = self.user_hidden_board
        if not hidden_board[int(x)][int(y)].empty:
            raise ValueError
        elif board[int(x)][int(y)].is_cabin:
            nr_hits += 1
            for p in board.plane_list():
                if int(p.row) == int(x) and int(p.col) == int(y):
                    dir = p.dir
                    neighbors = self._game.direction(int(x), int(y), dir)
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


class CompMoveTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._game = Game()
        self._ai = CompMove(self._game.user_board)

    def test_choose_coords(self):
        x, y = self._ai.choose_coords()
        self.assertLessEqual(x, 10)
        self.assertLessEqual(y, 10)

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None