#pragma once
#include "repository.h"

typedef struct
{
	RefrigeratorRepo* repo;
} RefrigeratorServ;

/// <summary>
/// Constructor for the service obj
/// </summary>
/// <param name="repo">The repo to be added to the service</param>
/// <returns>The service</returns>
RefrigeratorServ* createService(RefrigeratorRepo* repo);

/// <summary>
/// Destructor for the service object
/// </summary>
/// <param name="serv">The service to be destroyed</param>
void destroyService(RefrigeratorServ* serv);

/// <summary>
/// Add product to the service
/// </summary>
/// <param name="serv">The service to add a product</param>
/// <param name="name">The name of the new product to be added</param>
/// <param name="category">The category of the new product to be added</param>
/// <param name="quantity">The quantity of the new product to be added</param>
/// <param name="date">The date of the new product to be added</param>
/// <returns>1 if the product was successfully added, 0 if the quantity was updated, -1 otherwise</returns>
int addServ(RefrigeratorServ* serv, char name[], char category[], double quantity, char date[]);

/// <summary>
/// Add entries at start
/// </summary>
/// <param name="serv">The service to add a product</param>
/// <param name="name">The name of the new product to be added</param>
/// <param name="category">The category of the new product to be added</param>
/// <param name="quantity">The quantity of the new product to be added</param>
/// <param name="date">The date of the new product to be added</param>
/// <returns>1 if the product was successfully added, 0 if the quantity was updated, -1 otherwise</returns>
int addStart(RefrigeratorServ* serv, char name[], char category[], double quantity, char date[]);

/// <summary>
/// Remove product from the service
/// </summary>
/// <param name="serv">The service to remove a product</param>
/// <param name="name">The name of the product to be removed</param>
/// <param name="category">The category of the product to be removed</param>
/// <returns>1 if the product was successfully removed, 0 or -1 otherwise</returns>
int delServ(RefrigeratorServ* serv, char name[], char category[]);

/// <summary>
/// Update a product 
/// </summary>
/// <param name="serv">The service to update a product</param>
/// <param name="name">The name of the product to be updated</param>
/// <param name="category">The category of the product to be updated</param>
/// <param name="new_name">The new name</param>
/// <param name="new_category">The new category</param>
/// <param name="new_quantity">The new quantity</param>
/// <param name="new_date">The new date</param>
/// <returns>1 if the product was succesfully updated, 0 or -1 otherwise</returns>
int updateServ(RefrigeratorServ* serv, char name[], char category[], char new_name[], char new_category[], double new_quantity, char new_date[]);

/// <summary>
/// Gets the expiration day of a product
/// </summary>
/// <param name="serv">The service</param>
/// <param name="prod">The product</param>
/// <returns>The expiration day</returns>
int getExpirationDay(RefrigeratorServ* serv, Product prod);

/// <summary>
/// Gets the expiration month of a product
/// </summary>
/// <param name="serv">The service</param>
/// <param name="prod">The product</param>
/// <returns>The expiration month</returns>
int getExpirationMonth(RefrigeratorServ* serv, Product prod);

/// <summary>
/// Gets the expiration year of a product
/// </summary>
/// <param name="serv">The service</param>
/// <param name="prod">The product</param>
/// <returns>The expiration year</returns>
int getExpirationYear(RefrigeratorServ* serv, Product prod);

/// <summary>
/// Computes the number of days until the product expires
/// </summary>
/// <param name="exp_day">The expiration day</param>
/// <param name="exp_month">The expiration month</param>
/// <param name="exp_year">The expiration year</param>
/// <param name="current_day">The current day</param>
/// <param name="current_month">The current month</param>
/// <param name="current_year">The current year</param>
/// <returns>The number of days</returns>
int getNrOfDays(int exp_day, int exp_month, int exp_year, int current_day, int current_month, int current_year);
