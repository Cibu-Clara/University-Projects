"""
  Program functionalities module
"""
import copy


def create_expense(apartment_number, expense_amount, expense_type):
    """
    Create a new expense
    : param apartment_number: must be a positive integer
    : param expense_amount: must be a positive integer
    : param expense_type: must be an element of type_list
    : return: expense or None, if expense could not be created
    : except ValueError in case expense could not be created
    """
    type_list = ['water', 'heating', 'electricity', 'gas', 'other']
    if apartment_number >= 1 and expense_amount >= 1 and expense_type in type_list:
        return {'apartment': apartment_number, 'amount': expense_amount, 'type': expense_type}
    else:
        raise ValueError('Cannot create expense using given data')


# use getters & setters in order to access/ modify fields
def get_apartment(expense):
    return expense['apartment']


def get_amount(expense):
    return expense['amount']


def get_type(expense):
    return expense['type']


def set_expense(expense, value):
    expense['amount'] = value
    get_amount(expense)


def test_getters_and_setters():
    expense = create_expense(2, 100, 'gas')
    assert get_apartment(expense) == 2
    assert get_amount(expense) == 100
    assert get_type(expense) == 'gas'
    set_expense(expense, 120)
    assert get_amount(expense) == 120


def generate_expenses():  # generate 10 items at startup
    return [create_expense(1, 100, 'gas'), create_expense(2, 50, 'heating'), create_expense(3, 45, 'water'),
            create_expense(4, 150, 'electricity'), create_expense(5, 70, 'water'), create_expense(6, 15, 'other'),
            create_expense(7, 90, 'gas'), create_expense(8, 85, 'electricity'), create_expense(9, 120, 'water'),
            create_expense(10, 100, 'other')]


def add_expense(expense_list, expense):  # add a new expense to list
    expense_list.append(expense)


def add_expense_command(expense_list, cmd_param1, cmd_param2, cmd_param3):
    """
    add 13 gas 100         # add to apartment 13 an expense for gas in amount of 100 RON
    """
    try:
        expense = create_expense(int(cmd_param1), int(cmd_param2), cmd_param3)
        add_expense(expense_list, expense)
    except ValueError as ve:
        print(str(ve))


def test_add_expense():
    test_list = []
    add_expense(test_list, create_expense(12, 105, 'heating'))
    assert (len(test_list) == 1)
    assert (get_apartment(test_list[0]) == 12)


def remove_one(expense_list, cmd_param1):
    """
    remove 12           # remove all expenses for apartment 12
    """
    new_list = []
    for e in expense_list:
        if get_apartment(e) != int(cmd_param1):
            new_list.append(e)
    return new_list


def test_remove_one():
    test_list = [create_expense(10, 70, 'gas')]
    test_list = remove_one(test_list, 10)
    assert (len(test_list) == 0)


def remove_multiple(expense_list, cmd_param1):
    """
    remove gas         # remove all gas expenses from all apartments
    """
    new_list = []
    for e in expense_list:
        if get_type(e) != cmd_param1:
            new_list.append(e)
    return new_list


def test_remove_multiple():
    test_list = [create_expense(1, 50, 'gas'), create_expense(3, 20, 'heating'), create_expense(2, 70, 'gas')]
    test_list = remove_multiple(test_list, 'gas')
    assert (len(test_list) == 1)


def remove_interval(expense_list, cmd_param1, cmd_param2):
    """
    remove 5 to 10     # remove all expenses for apartments between 5 and 10
    """
    new_list = []
    for e in expense_list:
        if int(cmd_param1) > get_apartment(e) or get_apartment(e) > int(cmd_param2):
            new_list.append(e)
    return new_list


def test_remove_interval():
    test_list = [create_expense(1, 20, 'gas'), create_expense(2, 50, 'other'), create_expense(3, 70, 'gas')]
    test_list = remove_interval(test_list, 1, 3)
    assert (len(test_list) == 0)


def replace_expense(expense_list, cmd_param1, cmd_param2, cmd_param3):
    """
    replace 12 gas with 200     # replace the amount of the expense with type gas for apartment 12 with 200 RON
    """
    for e in expense_list:
        if get_apartment(e) == int(cmd_param1) and get_type(e) == cmd_param3:
            set_expense(e, int(cmd_param2))


def test_replace_expense():
    test_list = [create_expense(2, 30, 'gas')]
    replace_expense(test_list, 2, 50, 'gas')
    assert get_amount(test_list[0]) == 50


def total_expenses_apartment(expense_list):
    """
    Computes the total expense for each apartment and stores it in the dictionary 'costs'
    :param expense_list: List of expenses
    :returns dictionary costs, composed of apartment numbers and their total expenses
    """
    costs = {}
    for e in expense_list:
        if get_apartment(e) in costs:
            costs[get_apartment(e)] += get_amount(e)
        else:
            costs[get_apartment(e)] = get_amount(e)
    return costs


def test_total_expenses():
    test_list = [create_expense(1, 40, 'gas'), create_expense(1, 50, 'other'), create_expense(2, 90, 'heating')]
    costs = total_expenses_apartment(test_list)
    assert costs[1] == 90


def split_command(command):
    # separates the command and the possible positions where a certain action takes place
    tokens = command.split(maxsplit=4)
    cmd_word = tokens[0].lower()
    cmd_param1 = tokens[1] if len(tokens) >= 2 else None
    cmd_param2 = tokens[2] if len(tokens) == 3 else tokens[3] if len(tokens) == 4 else tokens[4] if len(tokens) == 5 \
        else None
    cmd_param3 = None
    if cmd_word == 'add':
        if len(tokens) == 4:
            cmd_param3 = tokens[2]
        else:
            raise IndexError('Not enough parameters')
    elif cmd_word == 'replace':
        if len(tokens) == 5:
            cmd_param3 = tokens[2]
        else:
            raise IndexError('Not enough parameters')
    return cmd_word, cmd_param1, cmd_param2, cmd_param3


def test_split_command():
    # assert crashes if False, does nothing if True
    assert split_command('eXiT') == ('exit', None, None, None)
    assert split_command('add 25 gas 100') == ('add', '25', '100', 'gas')
    assert split_command('Remove 15') == ('remove', '15', None, None)
    assert split_command('remove 5 to 10') == ('remove', '5', '10', None)
    assert split_command('remove electricity') == ('remove', 'electricity', None, None)
    assert split_command('replace 12 other with 20') == ('replace', '12', '20', 'other')
    assert split_command('list') == ('list', None, None, None)
    assert split_command('list 2') == ('list', '2', None, None)
    assert split_command('list  =   20') == ('list', '=', '20', None)


def sort_expense_list(expense_list):
    """
    :param expense_list: List of expenses
    :returns the sorted list
    """
    # sorts the expense list according to the apartment number
    copy_expense_list = expense_list.copy()
    copy_expense_list.sort(key=get_apartment, reverse=False)
    return copy_expense_list


def test_sort_list():
    test_list = [create_expense(3, 40, 'gas'), create_expense(4, 20, 'heating'), create_expense(1, 15, 'other')]
    test_list = sort_expense_list(test_list)
    assert (get_apartment(test_list[0]) == 1)


def total_amount_type(expense_list, cmd_param1):
    """
    sum gas          # display the total amount for the expenses having type gas

    :param expense_list: List of expenses
    :param cmd_param1: type
    :returns total amount
    """
    s = 0
    for e in expense_list:
        if get_type(e) == cmd_param1:
            s += get_amount(e)
    return s


def test_total_amount_type():
    test_list = [create_expense(1, 20, 'gas'), create_expense(3, 70, 'gas')]
    assert (total_amount_type(test_list, 'gas') == 90)


def sort_apartment(expense_list):
    """
    sort apartment         # display the list of apartments sorted ascending by total amount of expenses

    :param expense_list: List of expenses
    Computes the total of expenses for each apartment and stores it in dictionary 'costs', and then sorts it by value
    """
    costs = total_expenses_apartment(expense_list)
    return dict(sorted(costs.items(), key=lambda x: x[1], reverse=False))


def create_dict_type_amount(expense_list):
    """
    Creates a dictionary compound of types and the total amount per each one of them
    """
    type_list = ['water', 'heating', 'electricity', 'gas', 'other']
    amount_dict = {}
    for i in type_list:
        amount_dict[i] = total_amount_type(expense_list, i)
    return amount_dict


def test_create_dict_type_amount():
    test_list = [create_expense(1, 20, 'gas'), create_expense(2, 80, 'heating'), create_expense(9, 60, 'heating')]
    amount_dict = create_dict_type_amount(test_list)
    assert (amount_dict['heating'] == 140)


def sort_type(amount_dict):
    """
    sort type       # display the total amount of expenses for each type, sorted ascending by amount of money

    :param amount_dict: dictionary of total amount of expenses per each type
    :returns sorted dictionary by value
    """
    return dict(sorted(amount_dict.items(), key=lambda x: x[1], reverse=False))


def max_amount(expense_list, cmd_param1):
    """
    max 25          # display the maximum amount per each expense type for apartment 25

    :param expense_list: List of expenses
    :param cmd_param1 : the apartment number
    :returns a dictionary of types and their maximum amount for a certain apartment
    The dictionary is compound of two list: type_list and new_list which contains the maximum expense for each type
    """
    type_list = ['water', 'heating', 'electricity', 'gas', 'other']
    max_dict = {}
    for i in type_list:
        new_list = []
        for e in expense_list:
            if get_apartment(e) == int(cmd_param1) and get_type(e) == i:
                new_list.append(get_amount(e))
        if len(new_list):
            max_value = max(new_list)
        else:
            max_value = 0
        max_dict[i] = max_value
    return max_dict


def test_max_amount():
    test_list = [create_expense(20, 10, 'gas'), create_expense(20, 100, 'heating'), create_expense(20, 15, 'gas')]
    max_dict = max_amount(test_list, 20)
    assert (max_dict['gas'] == 15)


def filter_type(expense_list, cmd_param1):
    """
    filter gas          # keep only expenses for gas

    :param expense_list: List of expenses
    :param cmd_param1: type
    :returns the list containing only expenses of the given type
    """
    type_list = ['water', 'heating', 'electricity', 'gas', 'other']
    for i in type_list:
        if i != cmd_param1:
            expense_list = remove_multiple(expense_list, i)
    return expense_list


def test_filter_type():
    test_list = [create_expense(2, 30, 'gas'), create_expense(1, 70, 'electricity'), create_expense(3, 40, 'other')]
    test_list = filter_type(test_list, 'gas')
    assert (len(test_list) == 1)


def filter_value(expense_list, cmd_param1):
    """
    filter 300          # keep only expenses having an amount of money smaller than 300 RON

    :param expense_list: List of expenses
    :param cmd_param1: amount
    :returns the list containing only expenses smaller than the given amount
    """
    new_list = []
    for e in expense_list:
        if get_amount(e) < int(cmd_param1):
            new_list.append(e)
    return new_list


def test_filter_value():
    test_list = [create_expense(1, 20, 'heating'), create_expense(4, 100, 'gas')]
    test_list = filter_value(test_list, 50)
    assert (len(test_list) == 1)


def undo_last_command(stack):

    if len(stack) >= 0:
        expense_list_copy = copy.deepcopy(stack[-1])
        stack.pop()
        return expense_list_copy


def test_undo_function():
    expense_list_copy = []
    stack_copy = []
    add_expense(expense_list_copy, create_expense(10, 80, 'gas'))
    save_list(expense_list_copy, stack_copy)
    add_expense(expense_list_copy, create_expense(9, 70, 'other'))
    expense_list_copy = undo_last_command(stack_copy)
    assert len(expense_list_copy) == 1


def save_list(expense_list, stack):
    """
    Saves the list of expenses in stack every time before certain commands, so that we can undo that command
    """
    expense_list_copy = copy.deepcopy(expense_list)
    stack.append(expense_list_copy)


test_add_expense()
test_getters_and_setters()
test_sort_list()
test_remove_one()
test_remove_multiple()
test_split_command()
test_replace_expense()
test_remove_interval()
test_total_expenses()
test_total_amount_type()
test_create_dict_type_amount()
test_filter_value()
test_filter_type()
test_max_amount()
test_undo_function()
