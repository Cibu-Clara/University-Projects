class ScrambleRepo:
    def __init__(self):
        self.__swap_list = []

    def add_swap(self, swap):
        self.__swap_list.append(swap)

    def swap_list(self):
        return self.__swap_list

    def __len__(self):
        return len(self.__swap_list)

    def __getitem__(self, item):
        return self.__swap_list[item]
