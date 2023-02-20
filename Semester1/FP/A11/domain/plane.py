class Plane:
    def __init__(self, row, col, dir):
        self._row = row
        self._col = col
        self._direction = dir

    @property
    def row(self):
        return self._row

    @property
    def col(self):
        return self._col

    @property
    def dir(self):
        return self._direction
