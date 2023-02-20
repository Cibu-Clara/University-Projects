import unittest
from src.repository.studentRepo import StudentRepository
from src.domain.student import Student
from src.services.undoServ import FunctionCall, CascadedOperation, Operation, UndoServ
from src.validations.validation import validation_stud_name
from src.repository.gradeRepo import GradeRepository
from src.repository.assignRepo import AssignmentRepo


class StudentService:
    def __init__(self, undo_serv):
        self.__stud_repo = StudentRepository()
        self.__assign_repo = AssignmentRepo()
        self.__grade_repo = GradeRepository()
        self.__undo_serv = undo_serv

    def add_student(self, name):
        """
        :param name: the name of the student we want to add
        :return: None
        """
        validation_stud_name(name)
        self.__stud_repo.add_student(name)
        student_list = self.student_list()
        student = student_list[-1]
        fc_undo = FunctionCall(self.remove_student, student.stud_id)
        fc_redo = FunctionCall(self.add_student_undo, student.stud_id, student.name, student.group,
                               int(len(self.student_list()) - 1))
        cope = CascadedOperation(fc_undo, fc_redo)
        cope.add(Operation(fc_undo, fc_redo))
        self.__undo_serv.record_operation(cope)

    def add_student_undo(self, stud_id, name, group, pos):
        self.__stud_repo.add_student_undo(stud_id, name, group, pos)

    def remove_student(self, stud_id):
        """
        :param stud_id: The id of the student we want to remove
        :return: None
        """
        return self.__stud_repo.remove_student(stud_id)

    def update_student(self, stud_id, updated_name):
        """
        :param stud_id: The id of the student we want to update
        :param updated_name: The name we want to give to this student
        :return: None
        """
        name = self.__stud_repo.find_stud_by_id(stud_id)
        validation_stud_name(updated_name)
        self.__stud_repo.update_student(stud_id, updated_name)
        fc_undo = FunctionCall(self.__stud_repo.update_student, stud_id, name)
        fc_redo = FunctionCall(self.__stud_repo.update_student, stud_id, updated_name)
        cope = Operation(fc_undo, fc_redo)
        self.__undo_serv.record_operation(cope)

    def student_list(self):
        """
        :return: The current list
        """
        return self.__stud_repo.student_list()

    def find_stud_by_id(self, id_stud):
        """
        :param id_stud: The student's id
        :return: True if the student exists, None otherwise
        """
        return self.__stud_repo.find_stud_by_id(id_stud)

    def find_group(self, id_stud):
        """
        :param id_stud: The student's id
        :return: True if the student exists, None otherwise
        """
        return self.__stud_repo.find_group(id_stud)

    def find_group_by_id(self, id_group):
        """
        :param id_group: The group's id
        :return: True if the group exists, None otherwise
        """
        return self.__stud_repo.find_group_by_id(id_group)

    def __len__(self):
        return len(self.__stud_repo)

    def __getitem__(self, item):
        return self.__stud_repo[item]


class StudentServicesTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = StudentService(UndoServ)
        self._repo.add_student('Mary Parker')

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 21)
        self._repo.add_student(Student('1', 'Michael Christian', '917'))
        self._repo.add_student(Student('4', 'David Clara', '916'))
        self._repo.add_student(Student('0', 'Maria Luiza', '912'))
        self.assertEqual(len(self._repo), 24)

        # test remove
        last_student = self._repo[len(self._repo) - 1]
        id_last_student = last_student.stud_id
        self._repo.remove_student(id_last_student)
        self.assertEqual(len(self._repo), 23)

        # test update
        last_student = self._repo[len(self._repo) - 1]
        id_last_student = last_student.stud_id
        self._repo.update_student(id_last_student, "Robert Mihai")
        last_student = self._repo[len(self._repo) - 1]
        name_last_student = last_student.name
        self.assertEqual(name_last_student, "Robert Mihai")

        # test student list
        student_list = self._repo.student_list()
        self.assertEqual(student_list[len(student_list) - 1].stud_id, id_last_student)

        # test find student name by id
        name_last_student = self._repo.find_stud_by_id(id_last_student)
        self.assertEqual(name_last_student, "Robert Mihai")

        # test find group by id
        group_last_student = last_student.group
        ok = self._repo.find_group_by_id(group_last_student)
        self.assertEqual(ok, True)

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
