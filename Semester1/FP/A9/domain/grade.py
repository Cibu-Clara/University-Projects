class Grade:
    def __init__(self, stud_id, assignment_id, value):
        self.__stud_id = stud_id
        self.__assign_id = assignment_id
        self.__grade_value = value

    @property
    def stud_id(self):
        return self.__stud_id

    @stud_id.setter
    def stud_id(self, new_value):
        self.__stud_id = new_value

    @property
    def assignment_id(self):
        return self.__assign_id

    @assignment_id.setter
    def assignment_id(self, new_value):
        self.__assign_id = new_value

    @property
    def value(self):
        return self.__grade_value

    @value.setter
    def value(self, new_value):
        self.__grade_value = new_value

    def __str__(self):
        return str(self.stud_id) + " " + str(self.assignment_id) + " " + str(self.value)
