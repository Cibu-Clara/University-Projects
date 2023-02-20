from src.errors.error import ValidationErrors


def validation_stud_name(student_name):
    if student_name == "":
        raise ValidationErrors("Invalid name")


def validation_stud_id(stud_id):
    if stud_id == "":
        raise ValidationErrors("Invalid ID")


def validation_group(group):
    if group == "":
        raise ValidationErrors("Invalid group")


class ValidationStudents(object):
    pass


def validation_assign(description, year, month, day):
    if description == "":
        raise ValidationErrors("Invalid assignment")
    if year == "" or month == "" or day == "":
        raise ValidationErrors("Invalid deadline")
    elif year != 2021 and year != 2022:
        raise ValidationErrors("Invalid deadline")
    elif month > 12 or day > 31:
        raise ValidationErrors("Invalid deadline")


def validation_assign_id(assignment_id):
    if assignment_id == "":
        raise ValidationErrors("Invalid ID")


class ValidationAssignments(object):
    pass


def validation_grade(grade):
    if grade == "":
        raise ValidationErrors("Invalid grade")
    elif int(grade) < 0 or int(grade) > 10:
        raise ValidationErrors("The grade must be a value between 1 and 10")


class GradeValidation(object):
    pass
