import unittest
from src.domain.grade import Grade
from src.repository.gradeRepo import GradeRepository
from src.validations.validation import GradeValidation, validation_grade
from src.services.undoServ import UndoServ, FunctionCall, Operation, CascadedOperation


def average_grade(avg_list):
    """
    :param avg_list: The list of a student's grades
    :return: The arithmetic mean
    """
    s = 0.0
    for a in avg_list:
        s += float(a)
    return float(s/len(avg_list))


class GradeService:
    def __init__(self, stud, assign, undo_serv):
        self.__grade_repo = GradeRepository()
        self.__validation_grades = GradeValidation()
        self.__assign_serv = assign
        self.__stud_serv = stud
        self.__undo_serv = undo_serv

    def add_grade(self, stud_id, assignment_id, grade):
        """
        :param stud_id: the student we want to grade
        :param assignment_id: the assignment we want to grade the student at
        :param grade: the grade we want to give
        :return: None
        """
        grade_obj = Grade(stud_id, assignment_id, grade)
        validation_grade(grade)
        self.__grade_repo.add_grade(grade_obj)
        fc_undo = FunctionCall(self.__grade_repo.remove_grade, grade_obj)
        fc_redo = FunctionCall(self.__grade_repo.add_grade, grade_obj)
        cope = Operation(fc_undo, fc_redo)
        self.__undo_serv.record_operation(cope)

    def update_grade(self, stud_id, assignment_id, grade):
        """
        :param stud_id: the student we want to grade
        :param assignment_id: the assignment we want to grade the student at
        :param grade: the grade we want to give
        :return: None
        """
        grade_obj = Grade(stud_id, assignment_id, grade)
        validation_grade(grade)
        self.__grade_repo.update_grade(grade_obj)

    def remove_student_grades(self, stud_id):
        deleted_list = list()
        d = list()
        self.__grade_repo.remove_student_grades(stud_id, deleted_list)
        fc_undo = FunctionCall(self.__grade_repo.add_multiple_grades, deleted_list)
        fc_redo = FunctionCall(self.__grade_repo.remove_student_grades, stud_id, d)
        cope = CascadedOperation(fc_undo, fc_redo)
        cope.add(Operation(fc_undo, fc_redo))

        name = self.__stud_serv.find_stud_by_id(stud_id)
        group = self.__stud_serv.find_group(stud_id)
        index = self.__stud_serv.remove_student(stud_id)
        fc_undo = FunctionCall(self.__stud_serv.add_student_undo, stud_id, name, group, index)
        fc_redo = FunctionCall(self.__stud_serv.remove_student, stud_id)
        cope.add(Operation(fc_undo, fc_redo))
        self.__undo_serv.record_operation(cope)

    def remove_assignment(self, assignment_id):
        deleted_list = list()
        d = list()
        self.__grade_repo.remove_assignment(assignment_id, deleted_list)
        fc_undo = FunctionCall(self.__grade_repo.add_multiple_grades, deleted_list)
        fc_redo = FunctionCall(self.__grade_repo.remove_assignment, assignment_id, d)
        cope = CascadedOperation(fc_undo, fc_redo)
        cope.add(Operation(fc_undo, fc_redo))

        description = self.__assign_serv.find_assign_by_id(assignment_id)
        deadline = self.__assign_serv.find_assign_deadline(assignment_id)
        index = self.__assign_serv.remove_assignment(assignment_id)
        fc_undo = FunctionCall(self.__assign_serv.add_assign_undo, assignment_id, description, deadline, index)
        fc_redo = FunctionCall(self.__assign_serv.remove_assignment, assignment_id)
        cope.add(Operation(fc_undo, fc_redo))
        self.__undo_serv.record_operation(cope)

    def give_assignment_student(self, assign_id, student_id):
        if self.__assign_serv.find_assign_by_id(assign_id) and self.__stud_serv.find_stud_by_id(student_id):
            self.add_grade(student_id, assign_id, 0)

    def give_assignment_group(self, assign_id, group):
        if self.__assign_serv.find_assign_by_id(assign_id) and self.__stud_serv.find_group_by_id(group):
            for s in self.__stud_serv.student_list():
                if s.group == int(group):
                    self.add_grade(s.stud_id, assign_id, 0)

    def grade_list(self):
        """
        :return: The list of grades
        """
        return self.__grade_repo.grade_list()

    def __len__(self):
        return len(self.__grade_repo)

    def __getitem__(self, item):
        return self.__grade_repo[item]


class GradeServicesTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = GradeService([], [], UndoServ)
        self._repo.add_grade('0', '0', '10')

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 1)
        self._repo.add_grade('1', '1', '9')
        self._repo.add_grade('2', '2', '4')
        self._repo.add_grade('3', '3', '6')
        self.assertEqual(len(self._repo), 4)

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
