#include "repository.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <stdio.h>
RefrigeratorRepo* createRepo(int capacity)
{
    RefrigeratorRepo* repo = (RefrigeratorRepo*)malloc(sizeof(RefrigeratorRepo));
    if (repo == NULL)
        return NULL;

    repo->array = createDynamicArray(capacity);
    if (repo->array == NULL)
        return NULL;

    return repo;
}

RefrigeratorRepo DuplicateRepo(RefrigeratorRepo source)
{
    RefrigeratorRepo newRepo;

    DynamicArray* newArray = createDynamicArray(1);
    DuplicateArray(*source.array, newArray);

    newRepo.array = newArray;
    if (newRepo.array == NULL)
    {
        newRepo.array->capacity = 0;
        newRepo.array->length = 0;
    }

    return newRepo;
}

void destroyRepo(RefrigeratorRepo* repo)
{
    if (repo == NULL)
        return NULL;

    // destroy all products in repo
    destroy(repo->array);
    repo->array = NULL;

    free(repo);
}

int getRepoLength(RefrigeratorRepo* repo)
{
    return repo->array->length;
}

int addProduct(RefrigeratorRepo* repo, Product prod)
{   
    int pos = findProduct(repo, prod.name, prod.category);
    if (pos != -1)
    {   
        Product p = getProductOnPos(repo, pos);
        double new = p.quantity + prod.quantity;
        setQuantity(&repo->array->elems[pos], new);
        return 0;
    }

    add(repo->array, prod);
    return 1;
}

int deleteProduct(RefrigeratorRepo* repo, char name[], char category[])
{
    if (repo == NULL)
        return 0;

    int pos = findProduct(repo, name, category);
    if (pos == -1)
        return pos;
    for (int i = 0; i < getRepoLength(repo); i++) {
        if (pos == i) {
            del(repo->array, i);
            return 1;
        }
    }
}

int updateProduct(RefrigeratorRepo* repo, char name[], char category[], char new_name[], char new_category[], double new_quantity, char new_date[])
{
    if (repo == NULL)
        return 0;

    int pos = findProduct(repo, name, category);
    if (pos == -1)
        return pos;
    setName(&repo->array->elems[pos], new_name);
    setCategory(&repo->array->elems[pos], new_category);
    setQuantity(&repo->array->elems[pos], new_quantity);
    setDate(&repo->array->elems[pos], new_date);
    return 1;    
}

void sortByQuantity(RefrigeratorRepo* repo)
{
    for (int i = getRepoLength(repo) - 1; i > 0; i--)
    {
        for (int j = 0; j < i; j++)
        {
            Product p1 = getProductOnPos(repo, j);
            Product p2 = getProductOnPos(repo, j + 1);
            if (p1.quantity > p2.quantity)
            {
                interchange(repo->array, j, j + 1);
            }
        }
    }
}

int findProduct(RefrigeratorRepo* repo, char name[], char category[])
{   
    for (int i = 0; i < getRepoLength(repo); i++) {
        Product p = getProductOnPos(repo, i);
        if (strcmp(name, getName(p)) == 0 && strcmp(category, getCategory(p)) == 0)
        {
            return i;
        }
    }
    return -1;
}

void CopyRepo(RefrigeratorRepo sourceRepo, RefrigeratorRepo* destinationRepo)
{
    Copy(*sourceRepo.array, destinationRepo->array);
}


Product getProductOnPos(RefrigeratorRepo* repo, size_t pos)
{
    return repo->array->elems[pos];
}


