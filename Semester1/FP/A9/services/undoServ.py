class UndoServ:
    def __init__(self):
        # History of operations for undo/redo
        self._history = []
        # Our current position in undo/redo
        self._index = -1
        # Setting this to false stops recording operations for undo/redo
        self._record_flag = True

    def record_operation(self, operation):
        if self._record_flag is False:
            return

        if self._index != len(self._history) - 1:
            self._history = self._history[:self._index + 1]
            self._index = len(self._history) - 1
            self._history.append(0)
            self._index += 1
            self._history[self._index] = operation

        else:
            self._history.append(operation)
            self._index = len(self._history) - 1

    def undo(self):
        self._record_flag = False
        if self._index >= 0:
            self._history[self._index].undo()
            self._index -= 1
        else:
            self._record_flag = True
            raise Exception("Nothing to undo!")

        self._record_flag = True
        return True

    def redo(self):
        self._record_flag = False
        if self._index < len(self._history) - 1 and len(self._history) > 0:
            self._index += 1
            self._history[self._index].redo()
        else:
            self._record_flag = True
            raise Exception("Nothing to redo!")

        self._record_flag = True

    def history(self):
        return self._history


class Operation:
    def __init__(self, function_undo, function_redo):
        self._function_undo = function_undo
        self._function_redo = function_redo

    def undo(self):
        self._function_undo.call()

    def redo(self):
        self._function_redo.call()


class CascadedOperation(Operation):
    def __init__(self, function_undo, function_redo):
        super().__init__(function_undo, function_redo)
        self._operations = []

    def add(self, operation):
        self._operations.append(operation)

    def undo(self):
        for operation in self._operations:
            operation.undo()

    def redo(self):
        for operation in self._operations:
            operation.redo()


class FunctionCall:
    def __init__(self, function_name, *function_params):
        self._function_name = function_name
        self._function_params = function_params

    def call(self):
        self._function_name(*self._function_params)
