# non-UI section
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
    s = int(cmd_param2)
    for e in expense_list:
        if get_apartment(e) == int(cmd_param1) and get_type(e) == cmd_param3:
            s += get_amount(e)
    if s > int(cmd_param2):
        replace_expense(expense_list, int(cmd_param1), s, cmd_param3)
    else:
        try:
            expense = create_expense(int(cmd_param1), s, cmd_param3)
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
    remove gas         # remove all gas expenses for all apartments
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
            return {'apartment': get_apartment(e), 'amount': get_amount(e), 'type': get_type(e)}


def test_replace_expense():
    test_list = [create_expense(2, 30, 'gas')]
    replace_expense(test_list, 2, 50, 'gas')
    assert (get_amount(test_list[0]) == 50)


def total_expenses(expense_list):
    # computes the total expense for each apartment and stores it in the dictionary 'costs'
    costs = {}
    for e in expense_list:
        if get_apartment(e) in costs:
            costs[get_apartment(e)] += get_amount(e)
        else:
            costs[get_apartment(e)] = get_amount(e)
    return costs


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


def sort_list(expense_list):
    """
    : param expense_list: List of expenses
    : returns the sorted list
    """
    # sorts the expense list according to the apartment number
    copy_expense_list = expense_list.copy()
    copy_expense_list.sort(key=get_apartment, reverse=False)
    return copy_expense_list


def test_sort_list():
    test_list = [create_expense(3, 40, 'gas'), create_expense(4, 20, 'heating'), create_expense(1, 15, 'other')]
    test_list = sort_list(test_list)
    assert (get_apartment(test_list[0]) == 1)


test_add_expense()
test_getters_and_setters()
test_sort_list()
test_remove_one()
test_remove_multiple()
test_split_command()
test_replace_expense()
test_remove_interval()


# UI section
def print_all_expenses(expense_list):
    """
    list        # display all expenses
    """
    expense_list = sort_list(expense_list)
    for e in expense_list:
        print('apartment ' + str(get_apartment(e)) + ':' + str(get_type(e)) + ' ' + str(get_amount(e)))


def print_expense(expense_list, cmd_param1):
    """
    list 7      # display all expenses for apartment 7
    """
    for i in expense_list:
        if cmd_param1 == str(get_apartment(i)):
            print('apartment ' + str(get_apartment(i)) + ':' + str(get_type(i)) + ' ' + str(get_amount(i)))


def print_total(expense_list, cmd_param1, cmd_param2):
    """
    list > 100      # display all apartments having total expenses > 100 RON
    list = 17       # display all apartments having total expenses = 17 RON
    """
    costs = total_expenses(expense_list)
    expense_list = sort_list(expense_list)
    for e in expense_list:
        if cmd_param1 == '=' and costs[get_apartment(e)] == int(cmd_param2):
            print('apartment ' + str(get_apartment(e)))
            costs[get_apartment(e)] = 0
        elif cmd_param1 == '<' and costs[get_apartment(e)] < int(cmd_param2):
            print('apartment ' + str(get_apartment(e)))
            costs[get_apartment(e)] = 0
        elif cmd_param1 == '>' and costs[get_apartment(e)] > int(cmd_param2):
            print('apartment ' + str(get_apartment(e)))
            costs[get_apartment(e)] = 0
        elif cmd_param1 != '=' and cmd_param1 != '<' and cmd_param1 != '>':
            raise TypeError('First parameter should be a comparing symbol')


def start_command():
    type_list = ['water', 'heating', 'electricity', 'gas', 'other']
    expense_list = generate_expenses()  # initializing the list with the first 10 elements
    """
    add 13 gas 100              # add to apartment 13 an expense for gas in amount of 100 RON
    remove 12                   # remove all expenses for apartment 12
    remove gas                  # remove all gas expenses for all apartments
    remove 5 to 10              # remove all expenses for apartments between 5 and 10
    replace 12 gas with 200     # replace the amount of the expense with type gas for apartment 12 with 200 RON
    list                        # display all expenses
    list > 100                  # display all apartments having total expenses > 100 RON
    list = 17                   # display all apartments having total expenses = 17 RON
    """
    while True:
        try:
            command = input("Enter command")
            cmd_word, cmd_param1, cmd_param2, cmd_param3 = split_command(command)
            if cmd_word == 'add':
                if cmd_param1 is None or cmd_param2 is None or cmd_param3 is None:
                    raise TypeError('Invalid command')
                else:
                    add_expense_command(expense_list, cmd_param1, cmd_param2, cmd_param3)
            elif cmd_word == 'remove':
                if cmd_param1 is None:
                    raise TypeError('Invalid command')
                elif cmd_param2 is None:
                    if cmd_param1 in type_list:
                        expense_list = remove_multiple(expense_list, cmd_param1)
                    else:
                        expense_list = remove_one(expense_list, cmd_param1)
                else:
                    expense_list = remove_interval(expense_list, cmd_param1, cmd_param2)
            elif cmd_word == 'replace':
                if cmd_param1 is None or cmd_param2 is None or cmd_param3 is None:
                    raise TypeError('Invalid command')
                else:
                    replace_expense(expense_list, cmd_param1, cmd_param2, cmd_param3)
            elif cmd_word == 'list':
                if cmd_param1 is None:
                    print_all_expenses(expense_list)
                elif cmd_param2 is None:
                    print_expense(expense_list, cmd_param1)
                else:
                    print_total(expense_list, cmd_param1, cmd_param2)
            elif cmd_word == 'exit':
                return
            else:
                print('Invalid command')

        except ValueError as ve:
            print(str(ve))
        except TypeError as te:
            print(str(te))
        except IndexError as ie:
            print(str(ie))


start_command()
