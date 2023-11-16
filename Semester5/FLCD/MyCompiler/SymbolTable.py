SIZE = 10


class SymbolTable:
    def __init__(self):
        self.__capacity = SIZE
        self.__size = 0
        self.table = [None] * self.__capacity

    def __len__(self):
        return self.__size

    def hash(self, elem):
        if isinstance(elem, str):
            hash_value = 0
            for char in elem:
                hash_value += ord(char)
            return hash_value % self.__capacity
        elif isinstance(elem, int):
            return elem % self.__capacity

    def add(self, elem):
        self.__size += 1
        index = self.hash(elem)
        if self.table[index] is None:
            self.table[index] = [(elem, 0)]  # Use a tuple to store both the key and its position
        else:
            # Check if the element is already present in the list
            for i, (k, _) in enumerate(self.table[index]):
                if k == elem:
                    return
            # If not present, add the element with the next position
            positions = [pos for _, pos in self.table[index]]
            next_position = max(positions) + 1 if positions else 0
            self.table[index].append((elem, next_position))

    def contains(self, elem):
        index = self.hash(elem)
        if self.table[index] is not None:
            for k, _ in self.table[index]:
                if k == elem:
                    return True
        return False

    def get_position(self, elem):
        index = self.hash(elem)
        if self.contains(elem):
            for k, pos in self.table[index]:
                if k == elem:
                    return (index, pos)
        else:
            return (-1, -1)

    def get_elem_by_pos(self, row, column):
        for row_index, row_elems in enumerate(self.table):
            if row_elems is not None:
                for k, pos in row_elems:
                    if pos == column and row_index == row:
                        return k
        return None

    def is_empty(self):
        return self.__size == 0

    def __str__(self):
        result = []
        for index, elems in enumerate(self.table):
            if elems is not None:
                result.append(f"{index}: {', '.join([f'{k}: {v}' for k, v in elems])}")
            else:
                result.append(f"{index}: None")
        return "\n".join(result)


# symbol_table = SymbolTable()
# symbol_table.add(32)
# symbol_table.add("var1")
# symbol_table.add("hello")
# symbol_table.add("var1")
# print(symbol_table)
# print(symbol_table.get_position("var1"))
# print(symbol_table.get_position("hello"))
# print(symbol_table.get_position("world"))
# print(symbol_table.get_elem_by_pos(2, 0))
