#pragma once

typedef struct
{
    char name[50];
    char category[50];
    double quantity;
    char date[11];
}Product;

/// <summary>
/// Constructor for a Product object
/// </summary>
/// <param name="name">the name of the product</param>
/// <param name="category">the category of the product</param>
/// <param name="quantity">the quantity of the product</param>
/// <param name="date">the date of the product</param>
/// <returns>the created obj</returns>
Product createProduct(char name[], char category[], double quantity, char date[]);

// getters
char* getName(Product prod);
char* getCategory(Product prod);
double getQuantity(Product prod);
char* getDate(Product prod);

// setters
void setName(Product* prod, char new_name[]);
void setCategory(Product* prod, char new_category[]);
void setQuantity(Product* prod, double quantity);
void setDate(Product* prod, char new_date[]);

/// <summary>
/// Makes a copy of a product
/// </summary>
/// <param name="source_prod">the product to be copied</param>
/// <param name="dest_prod">the destination of the copy</param>
void assign(Product source_prod, Product* dest_prod);

/// <summary>
/// For printing the format of a product
/// </summary>
/// <param name="prod">the product obj</param>
/// <param name="str">the format to be printed</param>
void toString(Product prod, char str[]);

/// <summary>
/// A function to verify the params of a product introduced by the user
/// </summary>
/// <param name="name">The name of the product</param>
/// <param name="category">The category of the product</param>
/// <param name="quantity">The quantity of the product</param>
/// <param name="date">The date of the product</param>
/// <returns>1 if the params are valid, 0 otherwise</returns>
int validParams(char name[], char category[], double quantity, char date[]);

/// <summary>
/// Verification of the name
/// </summary>
/// <param name="name">The name of the product</param>
/// <returns>1 if the name is valid, 0 otherwise</returns>
int validName(char name[]);

/// <summary>
/// Verification of the category
/// </summary>
/// <param name="category">The category of the product</param>
/// <returns>1 if the category is valid, 0 otherwise</returns>
int validCategory(char category[]);

/// <summary>
/// Verification of the quantity
/// </summary>
/// <param name="quantity">The quantity of the product</param>
/// <returns>1 if the quantity is valid, 0 otherwise</returns>
int validQuantity(double quantity);

/// <summary>
/// Verification of the date
/// </summary>
/// <param name="date">The date of the product</param>
/// <returns>1 if the date is valid, 0 otherwise</returns>
int validDate(char date[]);