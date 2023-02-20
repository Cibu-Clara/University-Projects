from src.domain.cell import Cell
from texttable import Texttable


class Board:
    def __init__(self, rows, cols):
        self._rows = rows
        self._cols = cols
        self._board = [[Cell() for i in range(self._cols)] for j in range(self._rows)]
        self._planes = []

    @property
    def rows(self):
        return self._rows

    @property
    def cols(self):
        return self._cols

    def get_cell(self, row, col):
        return self._board[row][col]

    def plane_list(self):
        return self._planes

    def __getitem__(self, item):
        return self._board[item]

    def __str__(self):
        t = Texttable()
        row = [""]
        for col in range(1, self._cols):
            row.append(col)
        t.add_row(row)
        for i in range(1, self._rows):
            row = self._board[i]
            row[0] = chr(i + 64)
            t.add_row(row)
        return t.draw()

