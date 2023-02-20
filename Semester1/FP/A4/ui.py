"""
 User interface module
"""
from functions import get_apartment, get_amount, get_type, generate_expenses, add_expense_command, remove_one, \
    remove_multiple, remove_interval, replace_expense, total_expenses_apartment, split_command, sort_expense_list, \
    total_amount_type, sort_apartment, sort_type, create_dict_type_amount, max_amount, filter_type, filter_value, \
    undo_last_command, save_list


def print_all_expenses(expense_list):
    """
    list        # display all expenses
    """
    expense_list = sort_expense_list(expense_list)
    for e in expense_list:
        print('apartment ' + str(get_apartment(e)) + ':' + str(get_type(e)) + ' ' + str(get_amount(e)))


def print_expense(expense_list, cmd_param1):
    """
    list 7      # display all expenses for apartment 7
    """
    for e in expense_list:
        if cmd_param1 == str(get_apartment(e)):
            print('apartment ' + str(get_apartment(e)) + ':' + str(get_type(e)) + ' ' + str(get_amount(e)))


def print_total(expense_list, cmd_param1, cmd_param2):
    """
    list > 100      # display all apartments having total expenses > 100 RON
    list = 17       # display all apartments having total expenses = 17 RON
    """
    costs = total_expenses_apartment(expense_list)
    expense_list = sort_expense_list(expense_list)
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


def print_sort_apartment(expense_list):
    costs = sort_apartment(expense_list)
    for c in costs:
        print('apartment ' + str(c) + ':' + str(costs[c]))


def print_sort_type(expense_list):
    amount_dict = create_dict_type_amount(expense_list)
    amount_dict = sort_type(amount_dict)
    for i in amount_dict:
        print(i, amount_dict[i])


def print_max(expense_list, cmd_param1):
    max_dict = max_amount(expense_list, cmd_param1)
    for i in max_dict:
        print(i, max_dict[i])


def print_menu():
    # a command guide for the user
    print('\n      MENU OF COMMANDS \n add <apartment> <type> <amount> \n remove < apartment > \n remove < start '
          'apartment> to <end apartment> \n remove <type> \n replace <apartment> <type> with <amount> \n list \n '
          'list <apartment> \n list [ < | = | > ] <amount> \n sum <type> \n max <apartment> \n sort apartment \n '
          'sort type \n filter <type> \n filter <value> \n undo \n exit')


def start_command():
    type_list = ['water', 'heating', 'electricity', 'gas', 'other']
    expense_list = generate_expenses()  # initializing the list with the first 10 elements
    stack = []
    print_menu()
    """
    add 13 gas 100              # add to apartment 13 an expense for gas in amount of 100 RON
    remove 12                   # remove all expenses for apartment 12
    remove gas                  # remove all gas expenses for all apartments
    remove 5 to 10              # remove all expenses for apartments between 5 and 10
    replace 12 gas with 200     # replace the amount of the expense with type gas for apartment 12 with 200 RON
    list                        # display all expenses
    list > 100                  # display all apartments having total expenses > 100 RON
    list = 17                   # display all apartments having total expenses = 17 RON
    exit                        # exits the program
    """
    while True:
        try:
            command = input("Enter command: ")
            cmd_word, cmd_param1, cmd_param2, cmd_param3 = split_command(command)
            if cmd_word == 'add':
                save_list(expense_list, stack)
                if cmd_param1 is None or cmd_param2 is None or cmd_param3 is None:
                    raise TypeError('Invalid command')
                else:
                    add_expense_command(expense_list, cmd_param1, cmd_param2, cmd_param3)
            elif cmd_word == 'remove':
                save_list(expense_list, stack)
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
                save_list(expense_list, stack)
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
            elif cmd_word == 'sum':
                if cmd_param1 is None:
                    raise TypeError('Invalid command')
                else:
                    if cmd_param1 in type_list:
                        print(total_amount_type(expense_list, cmd_param1))
                    else:
                        raise ValueError('Invalid type')
            elif cmd_word == 'sort':
                if cmd_param1 == 'apartment':
                    print_sort_apartment(expense_list)
                elif cmd_param1 == 'type':
                    print_sort_type(expense_list)
                else:
                    raise TypeError('Invalid command')
            elif cmd_word == 'max':
                if cmd_param1 is None:
                    raise TypeError('Invalid command')
                else:
                    print_max(expense_list, cmd_param1)
            elif cmd_word == 'filter':
                save_list(expense_list, stack)
                if cmd_param1 is None:
                    raise TypeError('Invalid command')
                elif cmd_param1 in type_list:
                    expense_list = filter_type(expense_list, cmd_param1)
                else:
                    expense_list = filter_value(expense_list, cmd_param1)
            elif cmd_word == 'undo':
                if len(stack):
                    expense_list = undo_last_command(stack)
                else:
                    print('Nothing to undo')
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
