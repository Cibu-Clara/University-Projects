import unittest
import names
from random import randint
from src.domain.student import Student
from src.errors.error import RepositoryErrors


class StudentRepository:
    def __init__(self):
        self.__start_id = 1
        self.__end_id = 10
        self.__student_list = []
        for i in range(20):
            self.__student_list.append(Student(randint(self.__start_id, self.__end_id), names.get_full_name(),
                                               randint(911, 917)))
            self.__start_id = self.__end_id + 1
            self.__end_id += 20

    def add_student(self, name):
        """
        :param name: the name of the student we want to add to repo
        :return: None
        """
        self.__student_list.append(Student(randint(self.__start_id, self.__end_id), name, randint(911, 917)))
        self.__start_id = self.__end_id + 1
        self.__end_id += 10

    def add_student_undo(self, stud_id, name, group, pos):
        obj = Student(stud_id, name, group)
        self.__student_list.insert(pos, obj)

    def remove_student(self, student_id):
        """
        :param student_id: The id of the student we want to remove from repo
        :return: None
        """
        index = 0
        for stud in self.__student_list:
            current_id = int(stud.stud_id)
            if current_id == int(student_id):
                self.__student_list.remove(stud)
                return index
            index += 1

    def update_student(self, student_id, updated_name):
        """
        :param student_id: The id of the student we want to update
        :param updated_name: The name we want to give to this student
        :return: None
        """
        for i in range(len(self.__student_list)):
            if int(self.__student_list[i].stud_id) == int(student_id):
                self.__student_list[i].name = updated_name

    def student_list(self):
        """
        :return: The current list
        """
        return self.__student_list

    def find_stud_by_id(self, id_stud):
        """
        :param id_stud: The student's id
        :return: The name of the student if the student exists, None otherwise
        """
        for i in range(len(self.__student_list)):
            if int(self.__student_list[i].stud_id) == int(id_stud):
                return self.__student_list[i].name
        raise RepositoryErrors("The student with this id does not exist")

    def find_group(self, id_stud):
        """
        :param id_stud: The student's id
        :return: The group of the student if the student exists, None otherwise
        """
        for i in range(len(self.__student_list)):
            if int(self.__student_list[i].stud_id) == int(id_stud):
                return self.__student_list[i].group

    def find_group_by_id(self, id_group):
        """
        :param id_group: The group's id
        :return: True if the group exists, None otherwise
        """
        for i in range(len(self.__student_list)):
            if int(self.__student_list[i].group) == int(id_group):
                return True
        raise RepositoryErrors("The group with this id does not exist")

    def __len__(self):
        return len(self.__student_list)

    def __getitem__(self, item):
        return self.__student_list[item]


class StudentRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = StudentRepository()
        self._student = Student('100', 'Mary Parker', '912')
        self._repo.add_student(self._student)

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 21)
        self._repo.add_student(Student('1', 'Michael Christian', '917'))
        self._repo.add_student(Student('4', 'David Clara', '916'))
        self._repo.add_student(Student('0', 'Maria Luiza', '912'))
        self.assertEqual(len(self._repo), 24)

        # test remove
        last_student = self._repo[len(self._repo)-1]
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
        self.assertEqual(student_list[len(student_list)-1].stud_id, id_last_student)

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
