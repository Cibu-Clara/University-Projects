#include "ui.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

UI* createUI(RefrigeratorServ* serv, UndoService* undo_serv)
{
    UI* ui = (UI*)malloc(sizeof(UI));
    if (ui == NULL)
        return NULL;

    ui->serv = serv;
    ui->undo_serv = undo_serv;

    return ui;
}

void destroyUI(UI* ui)
{
    free(ui);
}

void printMenu()
{
    printf("\n");
    printf("1. Add a product.\n");
    printf("2. Delete a product.\n");
    printf("3. Update a product.\n");
    printf("4. Search for products by name.\n");
    printf("5. Search for products by category.\n");
    printf("6. Undo\n");
    printf("7. Redo\n");
    printf("8. List all products.\n");
    printf("0. Exit.\n");
}

int validCommand(int command)
{
    if (command >= 0 && command <= 8)
        return 1;
    return 0;
}

int readIntegerNumber(const char* message)
{
    char string[16] = { 0 };
    int result;
    int flag = 0;
    int read;

    while (flag == 0)
    {
        printf(message);
        int scanf_result = scanf("%15s", string);

        read = sscanf(string, "%d", &result); // reads data from s and store them as integer, return 1 if successful
        flag = (read == 1);

        if (flag == 0)
            printf("Invalid option! \n");

    }

    return result;
}

int addProductUI(UI* ui)
{
    char name[50], category[50], date[11];
    double quantity;

    printf("Please enter the name of the product: ");
    int scanf_result = scanf("%49s", name);

    int ok = 0;
    while (ok == 0)
    {
        printf("Please enter the category of the product (must be dairy, meat, sweets or fruit): ");
        scanf_result = scanf("%49s", category);
        category[49] = 0;
        if (strcmp(category, "dairy") == 0 || strcmp(category, "meat") == 0 || strcmp(category, "fruit") == 0 || strcmp(category, "sweets") == 0)
            ok = 1;
    }

    printf("Please enter the quantity of the product: ");
    scanf_result = scanf("%lf", &quantity);

    printf("Please enter the expiration date(format DD.MM.YYYY): ");
    scanf_result = scanf("%10s", date);

    return addServ(ui->serv, name, category, quantity, date);
}

int deleteProductUI(UI* ui)
{
    char name[50], category[50];
    printf("Please enter the name of the product you want to delete: ");
    int scanf_result = scanf("%49s", name);
    printf("Please enter the category of the product you want to delete: ");
    scanf_result = scanf("%49s", category);

    return delServ(ui->serv, name, category);
}

int updateProductUI(UI* ui)
{
    char name[50], new_name[50], category[50], new_category[50], new_date[11];
    double new_quantity;

    printf("Please enter the name of the product you want to update: ");
    int scanf_result = scanf("%49s", name);

    printf("Please enter the category of the product you want to update: ");
    scanf_result = scanf("%49s", category);

    printf("Please enter the new name of the product: ");
    scanf_result = scanf("%49s", new_name);

    int ok = 0;
    while (ok == 0)
    {
        printf("Please enter the new category of the product (must be dairy, meat, sweets or fruit): ");
        scanf_result = scanf("%49s", new_category);
        new_category[49] = 0;
        if (strcmp(new_category, "dairy") == 0 || strcmp(new_category, "meat") == 0 || strcmp(new_category, "fruit") == 0 || strcmp(new_category, "sweets") == 0)
            ok = 1;
    }

    printf("Please enter the new quantity of the product: ");
    scanf_result = scanf("%lf", &new_quantity);

    printf("Please enter the new expiration date of the product: ");
    scanf_result = scanf("%10s", new_date);

    return updateServ(ui->serv, name, category, new_name, new_category, new_quantity, new_date);
}

void listAllProducts(UI* ui)
{
    int ok = 0;
    if (ui == NULL)
        return;

    RefrigeratorRepo* repo = ui->serv->repo;
    int length = getRepoLength(repo);

    if (repo == NULL)
        return;

    for (int i = 0; i < length; i++)
    {
        ok = 1;
        Product prod = getProductOnPos(repo, i);
        char prodString[200];
        toString(prod, prodString);
        printf("%s\n", prodString);
    }
    if (ok == 0)
        printf("There are no products in refrigerator!\n");
}


void filterByNameUI(UI* ui)
{
    if (ui == NULL)
        return;

    RefrigeratorRepo* repo = ui->serv->repo;
    int length = getRepoLength(repo);

    if (repo == NULL)
        return;

    char given_name[50] = { 0 };
    printf("Please enter the string you want to search for in the products ('null' for all products): ");
    int result = scanf("%49s", given_name);
    RefrigeratorRepo* res = createRepo(10);

    int ok = 0;
    for (int i = 0; i < length; i++)
    {
        Product prod = getProductOnPos(repo, i);
        if (strcmp(given_name, "null") == 0 || strstr(prod.name, given_name))
        {
            ok = 1;
            addProduct(res, prod);
        }
    }
    if (ok == 0)
        printf("There are no products that contain the given string!\n");

    sortByQuantity(res);
    if (res == NULL)
        return;

    length = getRepoLength(res);
    for (int i = 0; i < length; i++)
    {
        Product prod = getProductOnPos(res, i);
        char prodString[200];
        toString(prod, prodString);
        printf("%s\n", prodString);
    }
    destroyRepo(res);
}

void filterByCategoryUI(UI* ui)
{
    if (ui == NULL)
        return;

    RefrigeratorRepo* repo = ui->serv->repo;
    int length = getRepoLength(repo);

    if (repo == NULL)
        return;

    char given_category[50] = { 0 };
    printf("Please enter a name of a category ('null' for all categories): ");
    int result = scanf("%49s", given_category);

    printf("Please enter a number of days: ");
    int days;
    scanf_s("%d", &days);

    time_t t = time(NULL);
    struct tm tm = *localtime(&t);
    int current_year = tm.tm_year + 1900;
    int current_month = tm.tm_mon + 1;
    int current_day = tm.tm_mday;
    int ok = 0;
    for (int i = 0; i < length; i++)
    {
        Product prod = getProductOnPos(repo, i);

        if (strcmp(given_category, "null") == 0 || strcmp(prod.category, given_category) == 0)
        {   
            ok = 1;
            int exp_day, exp_month, exp_year;
            exp_day = getExpirationDay(ui->serv, prod);
            exp_month = getExpirationMonth(ui->serv, prod);
            exp_year = getExpirationYear(ui->serv, prod);
            int to_days = getNrOfDays(exp_day, exp_month, exp_year, current_day, current_month, current_year);
            if (to_days <= days && to_days >= 0)
            {
                char prodString[200];
                toString(prod, prodString);
                printf("%s\n", prodString);
            }
            else
                ok = -1;
        }
    }
    if (ok == 0)
        printf("There are no products of the given category!\n");
    if (ok == -1)
        printf("No products of the given category expire in the following %d days!\n", days);
}

void startUI(UI* ui)
{
    while (1)
    {
        printMenu();

        int command = readIntegerNumber("Input command: ");

        while (validCommand(command) == 0)
        {
            printf("Please input a valid command!\n");
            command = readIntegerNumber("Input command: ");
        }
        if (command == 0)
            break;

        switch (command)
        {
        case 1:
        {
            int result = addProductUI(ui);

            if (result == 1)
            {
                RefrigeratorRepo repoCopy = DuplicateRepo(*ui->serv->repo);
                AddEntry(ui->undo_serv, repoCopy);
                printf("Product successfully added.\n");
            }
            else if (result == 0)
            {
                RefrigeratorRepo repoCopy = DuplicateRepo(*ui->serv->repo);
                AddEntry(ui->undo_serv, repoCopy);
                printf("Product already exists, quantity is updated.\n");
            }
            else
                printf("Couldn't add product!\n");
            break;
        }

        case 2:
        {
            int result = deleteProductUI(ui);
            if (result == 1)
            {
                RefrigeratorRepo repoCopy = DuplicateRepo(*ui->serv->repo);
                AddEntry(ui->undo_serv, repoCopy);
                printf("Product successfully deleted.\n");
            }
            else if (result == 0)
                printf("Error! Product could not be deleted, as there are no products in refrigerator!\n");
            else
                printf("Error! Product could not be deleted, as there doesn't exist a product with this name and category!\n");
            break;

        }

        case 3:
        {
            int result = updateProductUI(ui);
            if (result == 1)
            {
                RefrigeratorRepo repoCopy = DuplicateRepo(*ui->serv->repo);
                AddEntry(ui->undo_serv, repoCopy);
                printf("Product successfully updated.\n");
            }
            else if (result == 0)
                printf("Error! Product could not be updates, as there are no products in refrigerator!\n");
            else
                printf("Error! Product could not be updated, as there doesn't exist a product with this name and category!\n");
            break;

        }

        case 4:
        {
            filterByNameUI(ui);
            break;
        }
        case 5:
        {
            filterByCategoryUI(ui);
            break;
        }
        case 6:
        {
            int res = UndoAction(ui->undo_serv, ui->serv->repo);
            if (res == 1)
            {
                printf("Nothing to undo!\n");
            }
            else
                printf("Undo succesful!\n");
            break;
        }
        case 7:
        {
            int res = RedoAction(ui->undo_serv, ui->serv->repo);
            if (res == 1)
            {
                printf("Nothing to redo!\n");
            }
            else
                printf("Redo succesful!\n");
            break;
        }
        case 8:
        {
            listAllProducts(ui);
            break;
        }
        }
    }
}