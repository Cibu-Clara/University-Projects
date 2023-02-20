class Assignment:
    def __init__(self, assignment_id, description, deadline):
        self.__assign_id = assignment_id
        self.__assign_description = description
        self.__assign_deadline = deadline

    @property
    def assignment_id(self):
        return self.__assign_id

    @assignment_id.setter
    def assignment_id(self, new_value):
        self.__assign_id = new_value

    @property
    def description(self):
        return self.__assign_description

    @description.setter
    def description(self, new_value):
        self.__assign_description = new_value

    @property
    def deadline(self):
        return self.__assign_deadline

    @deadline.setter
    def deadline(self, new_value):
        self.__assign_deadline = new_value

    def __str__(self):
        return "ID: " + str(self.assignment_id) + '. ' + "Assignment: exercise " + str(self.description) + ', ' + \
               "Deadline: " + str(self.deadline)
