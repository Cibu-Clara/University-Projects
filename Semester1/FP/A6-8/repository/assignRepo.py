import random
import unittest
from random import randint
from src.domain.assignment import Assignment
from src.errors.error import RepositoryErrors
from datetime import datetime


def find_deadline_year(deadline):
    date = datetime.strptime(deadline, "%Y-%m-%d %H:%M:%S")
    return date.year


def find_deadline_month(deadline):
    date = datetime.strptime(deadline, "%Y-%m-%d %H:%M:%S")
    return date.month


def find_deadline_day(deadline):
    date = datetime.strptime(deadline, "%Y-%m-%d %H:%M:%S")
    return date.day


class AssignmentRepo:
    def __init__(self):
        self.__start_id = 1000
        self.__end_id = 1010
        self.__assignment_list = []
        for i in range(20):
            years = [2021, 2022]
            year = random.choice(years)
            day = randint(1, 28)
            month = randint(1, 12)
            self.__assignment_list.append(Assignment(randint(self.__start_id, self.__end_id),
                                                     randint(1, 50), datetime(year, month, day)))
            self.__start_id = self.__end_id + 1
            self.__end_id += 20

    def add_assignment(self, description, year, month, day):
        """
        :param description: the description of the assignment we want to add
        :param year: the deadline year of the assignment we want to add
        :param month: the deadline month of the assignment we want to add
        :param day: the deadline day of the assignment we want to add
        :return: None
        """
        deadline = datetime(year, month, day)
        self.__assignment_list.append(Assignment(randint(self.__start_id, self.__end_id), description, deadline))
        self.__start_id = self.__end_id + 1
        self.__end_id += 10

    def add_assign_undo(self, assign_id, description, deadline, pos):
        obj = Assignment(assign_id, description, deadline)
        self.__assignment_list.insert(pos, obj)

    def remove_assignment(self, assignment_id):
        """
        :param assignment_id: The id of the assignment we want to remove from repo (The description could be identical
                        to other)
        :return: None
        """
        index = 0
        for a in self.__assignment_list:
            current_id = int(a.assignment_id)
            if current_id == int(assignment_id):
                self.__assignment_list.remove(a)
                return index
            index += 1

    def update_assignment(self, assignment_id, updated_description, updated_year, updated_month, updated_day):
        """
        :param assignment_id: The id of the assignment we want to update
        :param updated_description: The description we want to give to this assignment
        :param updated_year: The deadline year we want to give to this assignment
        :param updated_month: The deadline month we want to give to this assignment
        :param updated_day: The deadline day we want to give to this assignment
        :return: None
        """
        for i in range(len(self.__assignment_list)):
            if int(self.__assignment_list[i].assignment_id) == int(assignment_id):
                self.__assignment_list[i].description = updated_description
                self.__assignment_list[i].deadline = datetime(updated_year, updated_month, updated_day)

    def assignment_list(self):
        """
        :return: The current list
        """
        return self.__assignment_list

    def find_assign_by_id(self, assign_id):
        """
        :param assign_id: The assignment's id
        :return: The description of the assignment if the assignment exists, None otherwise
        """
        for i in range(len(self.__assignment_list)):
            if int(self.__assignment_list[i].assignment_id) == int(assign_id):
                return self.__assignment_list[i].description
        raise RepositoryErrors("The assignment with this id does not exist")

    def find_assign_deadline(self, assign_id):
        """
        :param assign_id: The assignment's id
        :return: The deadline of the assignment if the assignment exists, None otherwise
        """
        for i in range(len(self.__assignment_list)):
            if int(self.__assignment_list[i].assignment_id) == int(assign_id):
                return self.__assignment_list[i].deadline

    def __len__(self):
        return len(self.__assignment_list)

    def __getitem__(self, item):
        return self.__assignment_list[item]


class AssignmentRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = AssignmentRepo()
        self._repo.add_assignment('12', 2021, 12, 20)

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 21)
        self._repo.add_assignment('10', 2021, 10, 15)
        self._repo.add_assignment('14', 2021, 9, 23)
        self._repo.add_assignment('20', 2021, 9, 20)
        self.assertEqual(len(self._repo), 24)

        # test remove
        last_assignment = self._repo[len(self._repo) - 1]
        id_last_assignment = last_assignment.assignment_id
        self._repo.remove_assignment(id_last_assignment)
        self.assertEqual(len(self._repo), 23)

        # test update
        last_assignment = self._repo[len(self._repo) - 1]
        id_last_assignment = last_assignment.assignment_id
        self._repo.update_assignment(id_last_assignment, '21', 2021, 2, 28)
        last_assignment = self._repo[len(self._repo) - 1]
        description_last_assignment = last_assignment.description
        self.assertEqual(description_last_assignment, '21')

        # test assignment list
        assignment_list = self._repo.assignment_list()
        self.assertEqual(assignment_list[22].description, '21')
        self.assertEqual(assignment_list[len(assignment_list) - 1].assignment_id, id_last_assignment)

        # test find assignment by id
        description_last_assignment = self._repo.find_assign_by_id(id_last_assignment)
        self.assertEqual(description_last_assignment, '21')

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
