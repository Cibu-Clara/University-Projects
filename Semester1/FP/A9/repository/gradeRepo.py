from src.domain.grade import Grade
import unittest
import pickle


class GradeRepository:
    def __init__(self):
        self.__grade_list = []

    def __len__(self):
        return len(self.__grade_list)

    def add_grade(self, grade_ob):
        """
        :param grade_ob: the object that contains student id, assignment id and the grade
        :return: False if the assignment was already assigned to that student, None otherwise
        """
        for g in self.grade_list():
            if int(g.assignment_id) == int(grade_ob.assignment_id) and int(g.stud_id) == int(grade_ob.stud_id):
                return False
        self.__grade_list.append(grade_ob)

    def add_multiple_grades(self, grades):
        for grade in grades:
            self.add_grade(grade)

    def update_grade(self, grade_ob):
        """
        :param grade_ob: the object that contains student id, assignment id and the grade
        :return: None
        """
        for g in self.grade_list():
            if int(g.assignment_id) == int(grade_ob.assignment_id) and int(g.stud_id) == int(grade_ob.stud_id) \
                    and int(g.value) == 0:
                g.value = grade_ob.value

    def remove_grade(self, obj):
        self.__grade_list.remove(obj)

    def remove_student_grades(self, stud_id, delete_list):
        """
        :param stud_id: The id of the student that is removed from the student list
        :param delete_list: A list containing the students who have been deleted
        :return: None
        """
        copy_list = self.__grade_list.copy()
        for grade in copy_list:
            if int(grade.stud_id) == int(stud_id):
                delete_list.append(grade)
                self.__grade_list.remove(grade)

    def remove_assignment(self, assignment_id, delete_list):
        """
        :param assignment_id: The id of the assignment that is removed from the assignment list
        :param delete_list: A list containing the assignments that have been deleted
        :return: None
        """
        copy_list = self.__grade_list.copy()
        for grade in copy_list:
            if int(grade.assignment_id) == int(assignment_id):
                delete_list.append(grade)
                self.__grade_list.remove(grade)

    def grade_list(self):
        """
        :return: The list of grades
        """
        return self.__grade_list

    def __getitem__(self, item):
        return self.__grade_list[item]

    def set_list(self, grade_list):
        self.__grade_list = grade_list


class GradeBinaryFileRepository(GradeRepository):
    def __init__(self):
        super().__init__()
        self.data = list()
        self.text_file = "grade.bin"
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
            self.data = self.grade_list()
            pickle.dump(self.data, f)

    def add_grade(self, grade_ob):
        super(GradeBinaryFileRepository, self).add_grade(grade_ob)
        self.save_file()

    def add_multiple_grades(self, grades):
        super(GradeBinaryFileRepository, self).add_multiple_grades(grades)
        self.save_file()

    def update_grade(self, grade_ob):
        super(GradeBinaryFileRepository, self).update_grade(grade_ob)
        self.save_file()

    def remove_grade(self, obj):
        super(GradeBinaryFileRepository, self).remove_grade(obj)
        self.save_file()

    def remove_student_grades(self, stud_id, delete_list):
        super(GradeBinaryFileRepository, self).remove_student_grades(stud_id, delete_list)
        self.save_file()

    def remove_assignment(self, assignment_id, delete_list):
        super(GradeBinaryFileRepository, self).remove_assignment(assignment_id, delete_list)
        self.save_file()


class GradeFileTextRepository(GradeRepository):
    def __init__(self):
        super().__init__()
        self.text_file = "grade.txt"
        self.load_file()

    def load_file(self):
        with open(self.text_file, "r") as f:
            for line in f.readlines():
                line = line.strip()
                line = line.replace("\n", "")
                if len(line) > 1:
                    stud_id, assignment_id, value = line.split(",", maxsplit=2)
                    obj = Grade(stud_id, assignment_id, value)
                    self.add_grade(obj)

    def save_file(self):
        with open(self.text_file, "w") as f:
            grade_list = self.grade_list()
            for grade in grade_list:
                f.write(str(grade.stud_id) + "," + str(grade.assignment_id) + "," + str(grade.value) + "\n")

    def add_grade(self, grade_ob):
        super(GradeFileTextRepository, self).add_grade(grade_ob)
        self.save_file()

    def add_multiple_grades(self, grades):
        super(GradeFileTextRepository, self).add_multiple_grades(grades)
        self.save_file()

    def update_grade(self, grade_ob):
        super(GradeFileTextRepository, self).update_grade(grade_ob)
        self.save_file()

    def remove_grade(self, obj):
        super(GradeFileTextRepository, self).remove_grade(obj)
        self.save_file()

    def remove_student_grades(self, stud_id, delete_list):
        super(GradeFileTextRepository, self).remove_student_grades(stud_id, delete_list)
        self.save_file()

    def remove_assignment(self, assignment_id, delete_list):
        super(GradeFileTextRepository, self).remove_assignment(assignment_id, delete_list)
        self.save_file()


class GradeRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = GradeRepository()
        self._grade = Grade('0', '0', '10')
        self._repo.add_grade(self._grade)

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 1)
        self._repo.add_grade(Grade('1', '1', '9'))
        self._repo.add_grade(Grade('2', '2', '4'))
        self._repo.add_grade(Grade('3', '3', '6'))
        self.assertEqual(len(self._repo), 4)

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment

        :return: None
        """
        self._repo = None
