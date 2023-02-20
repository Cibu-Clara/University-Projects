class Cell:
    def __init__(self):
        self._empty = True
        self._is_plane = False
        self._is_cabin = False

    @property
    def empty(self):
        return self._empty

    @empty.setter
    def empty(self, value):
        self._empty = value

    @property
    def is_plane(self):
        return self._is_plane

    @is_plane.setter
    def is_plane(self, value):
        self._is_plane = value

    @property
    def is_cabin(self):
        return self._is_cabin

    @is_cabin.setter
    def is_cabin(self, value):
        self._is_cabin = value

    def __str__(self):
        if self._empty:
            return ""
        if self._is_cabin:
            return "^"
        if not self._is_plane:
            return "o"
        else:
            return "x"
