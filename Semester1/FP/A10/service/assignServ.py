import unittest

from src.service.undoServ import FunctionCall, CascadedOperation, Operation, UndoServ
from src.validations.validation import validation_assign
from src.repository.assignRepo import AssignmentRepo, find_deadline_month, find_deadline_day, find_deadline_year


class AssignmentService:
    def __init__(self, undo_serv, repo_type):
        self.__assign_repo = repo_type
        self.__undo_serv = undo_serv

    def add_assignment(self, description, year, month, day):
        """
        :param description: the description of the assignment we want to add
        :param year: the deadline year of the assignment we want to add
        :param month: the deadline month of the assignment we want to add
        :param day: the deadline day of the assignment we want to add
        :return: None
        """
        validation_assign(description, year, month, day)
        self.__assign_repo.add_assignment(description, year, month, day)
        assignment_list = self.assignment_list()
        assignment = assignment_list[-1]
        fc_undo = FunctionCall(self.remove_assignment, assignment.assignment_id)
        fc_redo = FunctionCall(self.add_assign_undo, assignment.assignment_id, assignment.description,
                               assignment.deadline, int(len(self.assignment_list()) - 1))
        cope = CascadedOperation(fc_undo, fc_redo)
        cope.add(Operation(fc_undo, fc_redo))
        self.__undo_serv.record_operation(cope)

    def add_assign_undo(self, assign_id, description, deadline, pos):
        self.__assign_repo.add_assign_undo(assign_id, description, deadline, pos)

    def remove_assignment(self, assignment_id):
        """
        :param assignment_id: The id of the assignment we want to remove
        :return: None
        """
        return self.__assign_repo.remove_assignment(assignment_id)

    def update_assignment(self, assignment_id, updated_description, updated_year, updated_month, updated_day):
        """
        :param assignment_id: The id of the assignment we want to update
        :param updated_description: The description we want to give to this assignment
        :param updated_year: The deadline year we want to give to this assignment
        :param updated_month: The deadline month we want to give to this assignment
        :param updated_day: The deadline day we want to give to this assignment
        :return: None
        """
        description = self.__assign_repo.find_assign_by_id(assignment_id)
        deadline = str(self.__assign_repo.find_assign_deadline(assignment_id))
        year = find_deadline_year(deadline)
        month = find_deadline_month(deadline)
        day = find_deadline_day(deadline)
        validation_assign(updated_description, updated_year, updated_month, updated_day)
        self.__assign_repo.update_assignment(assignment_id, updated_description, updated_year, updated_month,
                                             updated_day)
        fc_undo = FunctionCall(self.__assign_repo.update_assignment, assignment_id, description, year, month, day)
        fc_redo = FunctionCall(self.__assign_repo.update_assignment, assignment_id, updated_description, updated_year,
                               updated_month, updated_day)
        cope = Operation(fc_undo, fc_redo)
        self.__undo_serv.record_operation(cope)

    def assignment_list(self):
        """
        :return: The current list
        """
        return self.__assign_repo.assignment_list()

    def find_assign_by_id(self, assignment_id):
        """
        :param assignment_id: The assignment's id
        :return: True if the assignment exists, None otherwise
        """
        return self.__assign_repo.find_assign_by_id(assignment_id)

    def find_assign_deadline(self, assignment_id):
        """
        :param assignment_id: The assignment's id
        :return: True if the assignment exists, None otherwise
        """
        return self.__assign_repo.find_assign_deadline(assignment_id)

    def __len__(self):
        return len(self.__assign_repo)

    def __getitem__(self, item):
        return self.__assign_repo[item]


class AssignmentServicesTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = AssignmentService(UndoServ, AssignmentRepo)
        self._repo.add_assignment('12', 2021, 11, 10)

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
