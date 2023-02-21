#include "service.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

RefrigeratorServ* createService(RefrigeratorRepo* repo)
{
    RefrigeratorServ* serv = (RefrigeratorServ*)malloc(sizeof(RefrigeratorServ));
    if (serv == NULL)
        return NULL;

    serv->repo = repo;

    return serv;
}

void destroyService(RefrigeratorServ* serv)
{
    free(serv);   // free the memory
}

int addServ(RefrigeratorServ* serv, char name[], char category[], double quantity, char date[])
{   
    if (!validParams(name, category, quantity, date)) 
        return -1;

    Product prod = createProduct(name, category, quantity, date);

    return addProduct(serv->repo, prod);
}

int addStart(RefrigeratorServ* serv, char name[], char category[], double quantity, char date[])
{
    Product prod = createProduct(name, category, quantity, date);

    return addProduct(serv->repo, prod);
}

int delServ(RefrigeratorServ* serv, char name[], char category[])
{
    return deleteProduct(serv->repo, name, category);
}

int updateServ(RefrigeratorServ* serv, char name[], char category[], char new_name[], char new_category[], double new_quantity, char new_date[])
{
    Product new_prod = createProduct(new_name, new_category, new_quantity, new_date);

    return updateProduct(serv->repo, name, category, new_name, new_category, new_quantity, new_date);
}

int getExpirationDay(RefrigeratorServ* serv, Product prod)
{
    char x[2] = "";
    x[0] = getDate(prod)[0];
    x[1] = getDate(prod)[1];
    int exp_day = atoi(x);
    return exp_day;
}

int getExpirationMonth(RefrigeratorServ* serv, Product prod)
{
    char x[2] = "";
    x[0] = getDate(prod)[3];
    x[1] = getDate(prod)[4];
    int exp_month = atoi(x);
    return exp_month;
}

int getExpirationYear(RefrigeratorServ* serv, Product prod)
{
    char x[4] = "";
    x[0] = getDate(prod)[6];
    x[1] = getDate(prod)[7];
    x[2] = getDate(prod)[8];
    x[3] = getDate(prod)[9];
    int exp_year = atoi(x);
    return exp_year;
}

int getNrOfDays(int exp_day, int exp_month, int exp_year, int current_day, int current_month, int current_year)
{
    /*
     Transform a date into days
    */
    int to_days = 0;
    if (exp_year > current_year)
        exp_month += 12;
    else if (exp_year < current_year)
        return -1;
    else if (exp_month < current_month)
        return -1;
    for (int i = current_month + 1; i < exp_month; i++) {
        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12)
            to_days += 31;
        else if (i == 2)
            to_days += 28;
        else
            to_days += 30;
    }
    if (current_month != exp_month)
    {
        to_days += exp_day;
        if (current_month == 1 || current_month == 3 || current_month == 5 || current_month == 7 || current_month == 8 || current_month == 10 || current_month == 12)
            to_days += (31 - current_day);
        else if (current_month == 2)
            to_days += (28 - current_day);
        else
            to_days += (30 - current_day);
    }
    else
        to_days = exp_day - current_day;

    return to_days;
}
