import unittest
import collection
from collection import Collection


class CollectionAndFilterTests(unittest.TestCase):
    def setUp(self):
        self._collection_list = Collection()
        for i in range(10):
            self._collection_list.append(i)

    def test_append(self):
        self._collection_list.append(10)
        self.assertEqual(self._collection_list[-1], 10)

    def test_len(self):
        self.assertEqual(len(self._collection_list), 10)

    def test_insert(self):
        self._collection_list.insert(2, 13)
        self.assertEqual(self._collection_list[2], 13)

    def test_remove(self):
        self._collection_list.remove(1)
        self.assertEqual(len(self._collection_list), 9)
        self._collection_list.remove(2)
        self.assertEqual(len(self._collection_list), 8)

    def test_list(self):
        my_list = self._collection_list.data
        self.assertEqual(my_list[0], 0)
        my_list = self._collection_list.copy()
        self.assertEqual(my_list[1], 1)

    def test_setitem(self):
        self._collection_list.__setitem__(0, 200)
        self.assertEqual(self._collection_list[0], 200)

    def test_delitem(self):
        self._collection_list.append(100)
        self._collection_list.append(90)
        self.assertEqual(self._collection_list[len(self._collection_list)-1], 90)
        self._collection_list.__delitem__(len(self._collection_list)-1)
        self.assertEqual(self._collection_list[len(self._collection_list) - 1], 100)

    def test_filter(self):
        self._collection_list.append(90)
        self._collection_list.append(90)
        self._collection_list.append(90)

        def pass_function(element):
            if int(element) == 90:
                return True
            return False

        my_list = collection.filter_data(self._collection_list, pass_function)
        self.assertEqual(len(my_list), 3)

    def tearDown(self) -> None:
        self._repo = None


def comparison_greater(elem1, elem2):
    if elem1 < elem2:
        return True
    return False


def comparison_smaller(elem1, elem2):
    if elem1 > elem2:
        return True
    return False


class SortTest(unittest.TestCase):
    def setUp(self) -> None:
        pass

    def tearDown(self) -> None:
        pass

    def test_sorting_ascending(self):
        my_list = [6, 10, 4, 9, 2, 8]
        my_list = collection.comb_sort(my_list, comparison_greater)
        self.assertEqual(my_list, [2, 4, 6, 8, 9, 10])

    def test_sorting_descending(self):
        my_list = [6, 10, 4, 9, 2, 8]
        my_list = collection.comb_sort(my_list, comparison_smaller)
        self.assertEqual(my_list, [10, 9, 8, 6, 4, 2])
