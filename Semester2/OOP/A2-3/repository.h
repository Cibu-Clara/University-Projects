#pragma once
#include "dynamicArray.h"
#include "domain.h"

typedef struct
{
    DynamicArray* array;
} RefrigeratorRepo;

/// <summary>
/// Constructor for the repo
/// </summary>
/// <param name="capacity">The maximum size of the repo</param>
/// <returns>The created repo</returns>
RefrigeratorRepo* createRepo(int capacity);

/// <summary>
/// Copy of the repo constructor
/// </summary>
/// <param name="source">The repo to be copied</param>
/// <returns>the duplicated repo</returns>
RefrigeratorRepo DuplicateRepo(RefrigeratorRepo source);

/// <summary>
/// Destructor for the repo obj
/// </summary>
/// <param name="repo">the repo to be destroyed</param>
void destroyRepo(RefrigeratorRepo* repo);

/// <summary>
/// Getter for the length of the repo
/// </summary>
/// <param name="repo">The repo to get the length of</param>
/// <returns>The size of the repo</returns>
int getRepoLength(RefrigeratorRepo* repo);

/// <summary>
/// Add a product object to the repo
/// </summary>
/// <param name="repo">The repo where to add the object</param>
/// <param name="prod">The product to be added to the repo</param>
/// <returns>1 if the product was successfully added, 0 if the quantity was updated, -1 otherwise</returns>
int addProduct(RefrigeratorRepo* repo, Product prod);

/// <summary>
/// Remove a product from the repo 
/// </summary>
/// <param name="repo">The repo where to remove a product from</param>
/// <param name="name">The name of the product to be removed</param>
/// <param name="category">The category of the product to be removed</param>
/// <returns>1 if the product was successfully removed, 0 or -1 otherwise</returns>
int deleteProduct(RefrigeratorRepo* repo, char name[], char category[]);

/// <summary>
/// Update a product 
/// </summary>
/// <param name="repo">The repo where to update a product</param>
/// <param name="name">The name of the product to be updated</param>
/// <param name="category">The category of the product to be updated</param>
/// <param name="new_name">The new name</param>
/// <param name="new_category">The new category</param>
/// <param name="new_quantity">The new quantity</param>
/// <param name="new_date">The new date</param>
/// <returns>1 if the product was succesfully updated, 0 or -1 otherwise</returns>
int updateProduct(RefrigeratorRepo* repo, char name[], char category[], char new_name[], char new_category[], double new_quantity, char new_date[]);

/// <summary>
/// Sorts the products ascending by their quantity
/// </summary>
/// <param name="repo">The repo where the products are stored</param>
void sortByQuantity(RefrigeratorRepo* repo);

/// <summary>
/// Copy of the repo
/// </summary>
/// <param name="sourceRepo">the repo to be copied</param>
/// <param name="destinationRepo">te destination of the copy</param>
void CopyRepo(RefrigeratorRepo sourceRepo, RefrigeratorRepo* destinationRepo);

/// <summary>
/// Getter of a product from the repo
/// </summary>
/// <param name="repo">The repo to look for a product</param>
/// <param name="pos">The position of the product</param>
/// <returns>The desired product object</returns>
Product getProductOnPos(RefrigeratorRepo* repo, size_t pos);