#include "domain.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

Product createProduct(char name[], char category[], double quantity, char date[])
{
	Product p;
	strcpy(p.name, name);
	strcpy(p.category, category);
	p.quantity = quantity;
	strcpy(p.date, date);
	return p;
}

char* getName(Product p)
{
	return p.name;
}

char* getCategory(Product p)
{
	return p.category;
}

double getQuantity(Product p)
{
	return p.quantity;
}

char* getDate(Product p)
{
	return p.date;
}

void setName(Product* prod, char new_name[])
{
	strcpy(prod->name, new_name);
}

void setCategory(Product* prod, char new_category[])
{
	strcpy(prod->category, new_category);
}

void setQuantity(Product* prod, double quantity)
{
	prod->quantity = quantity;
}

void setDate(Product* prod, char new_date[])
{
	strcpy(prod->date, new_date);
}

void assign(Product source_prod, Product* dest_prod)
{
	strcpy(dest_prod->name, source_prod.name);
	strcpy(dest_prod->category, source_prod.category);
	dest_prod->quantity = source_prod.quantity;
	strcpy(dest_prod->date, source_prod.date);
}

void toString(Product prod, char str[])
{	
	
	sprintf(str, "\nThe product %s belongs to the category %s, is in quantity of %.2lf kg and expires at the date of %s.", prod.name, prod.category, prod.quantity, prod.date);
}


int validParams(char name[], char category[], double quantity, char date[])
{
	if (!validName(name) || !validCategory(category) || !validQuantity(quantity) || !validDate(date))
		return 0;
	return 1;
}

int validName(char name[])
{
	if (strlen(name) == 0) return 0;
	return 1;
}

int validCategory(char category[])
{
	if (strlen(category) == 0) return 0;
	return 1;
}

int validQuantity(double quantity)
{
	if (quantity > 0x3f3f3f3f) return 0;
	return 1;
}

int validDate(char date[])
{
	if (strlen(date) == 0) return 0;
	return 1;
}
