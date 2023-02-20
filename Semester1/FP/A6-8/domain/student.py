class Student:
    def __init__(self, stud_id,  name, group):
        self.__stud_id = stud_id
        self.__stud_name = name
        self.__stud_group = group

    @property
    def stud_id(self):
        return self.__stud_id

    @stud_id.setter
    def stud_id(self, new_value):
        self.__stud_id = new_value

    @property
    def name(self):
        return self.__stud_name

    @name.setter
    def name(self, new_value):
        self.__stud_name = new_value

    @property
    def group(self):
        return self.__stud_group

    @group.setter
    def group(self, new_value):
        self.__stud_group = new_value

    def __str__(self):
        return "ID: " + str(self.stud_id) + '. ' + "Student " + str(self.name) + ', ' + "Group " + str(self.group)
