class Iterator:
    def __init__(self, collection):
        self.__collection = collection
        self.__position = 0

    def __next__(self):
        if self.__position == len(self.__collection.data):
            raise StopIteration()
        self.__position += 1
        return self.__collection.data[self.__position - 1]


class Collection:
    def __init__(self):
        self.__data = []

    @property
    def data(self):
        return self.__data

    def append(self, value):
        self.__data.append(value)

    def insert(self, pos, value):
        self.__data.insert(pos, value)

    def remove(self, obj):
        self.__data.remove(obj)

    def __len__(self):
        return len(self.__data)

    def __iter__(self):
        return Iterator(self)

    def __setitem__(self, key, value):
        self.__data[key] = value

    def __getitem__(self, key):
        return self.__data[key]

    def __delitem__(self, key):
        del self.__data[key]

    def copy(self):
        return self.__data.copy()


def filter_data(data, pass_function):
    """
    Filters data by a given pass function
    :param data: the data to be filtered
    :param pass_function: a pass function used in filtering data
    :return: the list of filtered elements
    """

    return [element for element in data if pass_function(element)]


def comb_sort(data, comparison_function):
    """
    Comb sort improves on bubble sort.
    The basic idea is to eliminate turtles, since in a bubble sort these slow the sorting down tremendously.
    Rabbits do not pose a problem in bubble sort.

    turtles - small values near the end of the list
    rabbits  - large values around the beginning of the list

    The basic idea of comb sort is that the gap can be much more than 1. The inner loop of bubble sort, which does the
    actual swap, is modified such that the gap between swapped elements goes down (for each iteration of outer loop) in
    steps of a "shrink factor" k: [ n/k, n/k^2, n/k^3, ..., 1 ].

    WC: O(n^2)
    BC: O(n * log(n))
    AC: O(n^2 / 2^p), where p - the number of increments

    :param data: the data to be sorted
    :param comparison_function: a comparison function used in comparing the elements of data
    :return: the sorted data
    """
    length = len(data)

    # initialize the gap
    gap = length
    # initialize swapped as true to make sure that the loop runs
    swapped = True

    # keep running while gap > 1 and last iteration caused a swap (swapped = true)
    while swapped:
        # update the gap: we use the shrink factor 1.3
        gap = (gap * 10) // 13
        if gap <= 1:
            gap = 1
            swapped = False  # we might stop now (unless we find unordered elements)

        # compare all the elements with the current gap
        for i in range(0, length - gap):  # there are length - gap such pairs
            if comparison_function(data[i + gap], data[i]):
                data[i], data[i + gap] = data[i + gap], data[i]  # then swap the two elements
                swapped = True
    return data


