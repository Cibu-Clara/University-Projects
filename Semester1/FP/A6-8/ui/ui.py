from src.services.studentServ import StudentService
from src.services.assignServ import AssignmentService
from src.services.gradeServ import GradeService, average_grade
from src.services.undoServ import UndoServ
from src.validations.validation import ValidationErrors, validation_stud_id, validation_assign_id, validation_group
from src.errors.error import RepositoryErrors
from datetime import datetime


def show_menu():
    print("1. List")
    print("2. Add")
    print("3. Remove")
    print("4. Update")
    print("5. Give assignment")
    print("6. Grade student at a given assignment")
    print("7. Show given assignments")
    print("8. Show statistics")
    print("9. Undo")
    print("10. Redo")
    print("11. Exit")


class Console:
    def __init__(self):
        self._undo_serv = UndoServ()
        self._stud_serv = StudentService(self._undo_serv)
        self._assign_serv = AssignmentService(self._undo_serv)
        self._grade_serv = GradeService(self._stud_serv, self._assign_serv, self._undo_serv)

    def list_students(self):
        display_list = self._stud_serv.student_list()
        for student in display_list:
            print(student)

    def list_assignments(self):
        display_list = self._assign_serv.assignment_list()
        for assignment in display_list:
            print(assignment)

    def add_student_ui(self):
        try:
            n = int(input("Enter how many students to be added"))
            for i in range(n):
                name = input("Enter name")
                self._stud_serv.add_student(name)
        except ValidationErrors as va:
            print(str(va))

    def add_assignment_ui(self):
        try:
            n = int(input("Enter how many assignments to be added"))
            for i in range(n):
                description = input("Enter assignment")
                print("Enter deadline")
                day = int(input("    day:"))
                month = int(input("    month:"))
                year = int(input("    year:"))
                self._assign_serv.add_assignment(description, year, month, day)
        except ValidationErrors as va:
            print(str(va))

    def remove_student_ui(self):
        stud_id = input("Enter the id of the student you want to remove")
        try:
            validation_stud_id(stud_id)
            self._grade_serv.remove_student_grades(stud_id)
            # self._stud_serv.remove_student(stud_id)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def update_student_ui(self):
        stud_id = input("Enter the id of the student you want to update")
        stud_name = input("Enter the new name of this student")
        try:
            validation_stud_id(stud_id)
            self._stud_serv.update_student(stud_id, stud_name)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def remove_assignment_ui(self):
        try:
            assignment_id = input("Enter the id of the assignment you want to remove")
            validation_assign_id(assignment_id)
            self._grade_serv.remove_assignment(assignment_id)
            # self._assign_serv.remove_assignment(assignment_id)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def update_assignment_ui(self):
        assignment_id = input("Enter the id of the assignment you want to update")
        description = input("Enter the new description of this assignment")
        print("Enter deadline")
        try:
            validation_assign_id(assignment_id)
            day = int(input("    day:"))
            month = int(input("    month:"))
            year = int(input("    year:"))
            self._assign_serv.update_assignment(assignment_id, description, year, month, day)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def give_assignment_student_ui(self):
        assignment_id = input("Enter the id of the assignment you want to give")
        stud_id = input("Enter the id of the student who receives the assignment")
        try:
            validation_assign_id(assignment_id)
            validation_stud_id(stud_id)
            self._grade_serv.give_assignment_student(assignment_id, stud_id)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def give_assignment_group_ui(self):
        assignment_id = input("Enter the id of the assignment you want to give")
        group = input("Enter the name of the group that receives the assignment")
        try:
            validation_assign_id(assignment_id)
            validation_group(group)
            self._grade_serv.give_assignment_group(assignment_id, group)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def grade_student_ui(self):
        stud_id = input("Enter the id of the student you want to grade")
        try:
            validation_stud_id(stud_id)
            if self._stud_serv.find_stud_by_id(stud_id):
                print("The ungraded exercises assigned to this student are:")
                if not self.choose_assignment(stud_id):
                    return
                assignment_id = input("\nSelect the assignment to be graded")
                if self._assign_serv.find_assign_by_id(assignment_id):
                    value = input("Enter the grade")
                    self._grade_serv.update_grade(stud_id, assignment_id, value)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def choose_assignment(self, stud_id):
        display_list = list()
        grade_list = self._grade_serv.grade_list()
        index = 1
        for grade in grade_list:
            if int(grade.stud_id) == int(stud_id) and grade.value == 0:
                assignment = self._assign_serv.find_assign_by_id(grade.assignment_id)
                display_list.append([index, grade.assignment_id, assignment])
                index += 1
        if len(display_list) == 0:
            print("There are no ungraded assignments for this student")
            return False
        for i in display_list:
            print(str(i[0]) + ". Assignment " + str(i[1]) + ": exercise " + str(i[2]))
        return True

    def list_grades(self):
        display_list = list()
        grade_list = self._grade_serv.grade_list()
        for grade in grade_list:
            name = self._stud_serv.find_stud_by_id(grade.stud_id)
            group = self._stud_serv.find_group(grade.stud_id)
            assignment = self._assign_serv.find_assign_by_id(grade.assignment_id)
            deadline = self._assign_serv.find_assign_deadline(grade.assignment_id)
            display_list.append([grade.stud_id, name, grade.assignment_id, assignment, grade.value, group, deadline])
        for i in display_list:
            print("Student " + str(i[1]) + " from group " + str(i[5]) + " with ID: " + str(i[0]) +
                  ", Assignment with ID: " + str(i[2]) + " - exercise " + str(i[3]) + ", deadline: " + str(i[6]) +
                  ", Grade: " + str(i[4]))
        if len(display_list) == 0:
            print("There are no given assignments")

    def statistics_given_assignment(self):
        assign_id = input("Enter assignment ID")
        display_list = list()
        grade_list = self._grade_serv.grade_list()
        try:
            validation_assign_id(assign_id)
            if self._assign_serv.find_assign_by_id(assign_id):
                for grade in grade_list:
                    if int(grade.assignment_id) == int(assign_id) and grade.value != 0:
                        name = self._stud_serv.find_stud_by_id(grade.stud_id)
                        group = self._stud_serv.find_group(grade.stud_id)
                        display_list.append([grade.stud_id, name, group, grade.value])
            display_list.sort(key=lambda x: int(x[3]), reverse=True)
            index = 1
            for i in display_list:
                print(str(index) + ". Student " + str(i[1]) + " from group " + str(i[2]) + " with ID " + str(i[0]) +
                      ": grade " + str(i[3]))
                index += 1
            if len(display_list) == 0:
                print("There are no statistics of this assignment")
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def statistics_delay(self):
        grade_list = self._grade_serv.grade_list()
        stud_list = self._stud_serv.student_list()
        display_list = list()
        today = datetime.now()
        for stud in stud_list:
            for grade in grade_list:
                if int(grade.stud_id) == int(stud.stud_id) and grade.value == 0:
                    deadline = self._assign_serv.find_assign_deadline(grade.assignment_id)
                    if deadline < today:
                        display_list.append([stud.stud_id, stud.name, stud.group, grade.assignment_id, deadline])
        index = 1
        for i in display_list:
            print(str(index) + ". Student " + str(i[1]) + " from group " + str(i[2]) + " with ID: " + str(i[0]) +
                  " is late with assignment nr. " + str(i[3]) + " - deadline: " + str(i[4]))
            index += 1
        if len(display_list) == 0:
            print("There is no student who is late in handing in assignments")

    def statistics_school_situation(self):
        grade_list = self._grade_serv.grade_list()
        stud_list = self._stud_serv.student_list()
        display_list = list()
        for stud in stud_list:
            avg_list = []
            for grade in grade_list:
                if int(grade.stud_id) == int(stud.stud_id) and grade.value != 0:
                    avg_list.append(grade.value)
            if len(avg_list):
                avg = average_grade(avg_list)
                display_list.append([stud.stud_id, stud.name, stud.group, avg])
        display_list.sort(key=lambda x: x[3], reverse=True)
        index = 1
        for i in display_list:
            print(str(index) + ". Student " + str(i[1]) + " from group " + str(i[2]) + " with ID " + str(i[0]) +
                  ": average grade " + str(i[3]))
            index += 1
        if len(display_list) == 0:
            print("The school situation is incomplete")

    def start(self):
        while True:
            try:
                show_menu()
                opt = input("Enter an option")
                if opt in ["1", "2", "3", "4"]:
                    print("1. Students")
                    print("2. Assignments")
                if opt == "1":
                    opt2 = input("Enter an option")
                    if opt2 == "1":
                        self.list_students()
                    elif opt2 == "2":
                        self.list_assignments()
                    else:
                        raise ValueError("Invalid option")
                elif opt == "2":
                    opt2 = input("Enter an option")
                    if opt2 == "1":
                        self.add_student_ui()
                    elif opt2 == "2":
                        self.add_assignment_ui()
                    else:
                        raise ValueError("Invalid option")
                elif opt == '3':
                    opt2 = input("Enter an option")
                    if opt2 == "1":
                        self.remove_student_ui()
                    elif opt2 == "2":
                        self.remove_assignment_ui()
                    else:
                        raise ValueError("Invalid option")
                elif opt == '4':
                    opt2 = input("Enter an option")
                    if opt2 == "1":
                        self.update_student_ui()
                    elif opt2 == "2":
                        self.update_assignment_ui()
                    else:
                        raise ValueError("Invalid option")
                elif opt == '5':
                    print("1. To a student")
                    print("2. To a group of students")
                    opt2 = input("Enter an option")
                    if opt2 == "1":
                        self.give_assignment_student_ui()
                    elif opt2 == "2":
                        self.give_assignment_group_ui()
                    else:
                        raise ValueError("Invalid option")
                elif opt == '6':
                    self.grade_student_ui()
                elif opt == '7':
                    self.list_grades()
                elif opt == '8':
                    print("1. Of a given assignment")
                    print("2. Of late handed-in assignments")
                    print("3. Of the school situation")
                    opt2 = input("Enter an option")
                    if opt2 == '1':
                        self.statistics_given_assignment()
                    elif opt2 == '2':
                        self.statistics_delay()
                    elif opt2 == '3':
                        self.statistics_school_situation()
                    else:
                        raise ValueError("Invalid option")
                elif opt == '9':
                    self._undo_serv.undo()
                elif opt == '10':
                    self._undo_serv.redo()
                elif opt == '11':
                    return
                else:
                    print("Invalid option")
            except ValueError as ve:
                print(str(ve))
            except Exception as ex:
                print(str(ex))


ui = Console()
ui.start()
