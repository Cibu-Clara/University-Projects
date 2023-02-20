import unittest
import names
import pickle
from random import randint
from src.domain.student import Student
from src.errors.error import RepositoryErrors
from src.repository.collection import Collection


class StudentRepository:
    def __init__(self):
        self.__start_id = 1
        self.__end_id = 10
        self.__student_list = Collection()
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

    def add_student_by_id(self, stud_id, name, group):
        obj = Student(stud_id, name, group)
        self.__student_list.append(obj)

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

    def set_list(self, student_list):
        self.__student_list = student_list


class StudentBinaryFileRepository(StudentRepository):
    def __init__(self):
        super().__init__()
        self.data = list()
        self.text_file = "students.bin"
        self.load_file()

    def load_file(self):
        with open(self.text_file, "rb") as f:
            try:
                self.data = pickle.load(f)
                self.set_list(self.data)
            except EOFError:
                pass

    def save_file(self):
        with open(self.text_file, "wb") as f:
            self.data = self.student_list()
            pickle.dump(self.data, f)

    def add_student(self, name):
        super(StudentBinaryFileRepository, self).add_student(name)
        self.save_file()

    def add_student_undo(self, stud_id, name, group, pos):
        super(StudentBinaryFileRepository, self).add_student_undo(stud_id, name, group, pos)
        self.save_file()

    def add_student_by_id(self, stud_id, name, group):
        super(StudentBinaryFileRepository, self).add_student_by_id(stud_id, name, group)
        self.save_file()

    def remove_student(self, student_id):
        aux = super(StudentBinaryFileRepository, self).remove_student(student_id)
        self.save_file()
        return aux

    def update_student(self, student_id, updated_name):
        super(StudentBinaryFileRepository, self).update_student(student_id, updated_name)
        self.save_file()


class StudentFileTextRepository(StudentRepository):
    def __init__(self):
        super().__init__()
        self.text_file = "students.txt"
        self.load_file()

    def load_file(self):
        with open(self.text_file, "r") as f:
            for line in f.readlines():
                line = line.strip()
                line = line.replace("\n", "")
                if len(line) > 1:
                    stud_id, name, group = line.split(",", maxsplit=2)
                    self.add_student_by_id(stud_id, name, group)

    def save_file(self):
        with open(self.text_file, "w") as f:
            student_list = self.student_list()
            for stud in student_list:
                f.write(str(stud.stud_id) + "," + str(stud.name) + "," + str(stud.group) + "\n")

    def add_student(self, name):
        super(StudentFileTextRepository, self).add_student(name)
        self.save_file()

    def add_student_undo(self, stud_id, name, group, pos):
        super(StudentFileTextRepository, self).add_student_undo(stud_id, name, group, pos)
        self.save_file()

    def add_student_by_id(self, stud_id, name, group):
        super(StudentFileTextRepository, self).add_student_by_id(stud_id, name, group)
        self.save_file()

    def remove_student(self, student_id):
        aux = super(StudentFileTextRepository, self).remove_student(student_id)
        self.save_file()
        return aux

    def update_student(self, student_id, updated_name):
        super(StudentFileTextRepository, self).update_student(student_id, updated_name)
        self.save_file()


class StudentRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = StudentRepository()
        self._repo.add_student('Mary Parker')

    '''
    Define test functions (test cases) using functions named test_*
    '''

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 21)
        self._repo.add_student('Michael Christian')
        self._repo.add_student('David Clara')
        self._repo.add_student('Maria Luiza')
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
