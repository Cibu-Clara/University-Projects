#pragma once
#define CAPACITY 10
#include "domain.h"

typedef Product TElement;

typedef struct
{
	TElement* elems;
	int length;			// actual length of the array
	int capacity;		// maximum capacity of the array
} DynamicArray;

DynamicArray* createDynamicArray(int capacity);
void destroy(DynamicArray* arr);

void DuplicateArray(DynamicArray source, DynamicArray* destination);

int getLength(DynamicArray* arr);

void add(DynamicArray* arr, TElement t);
void del(DynamicArray* arr, int pos);

TElement getElem(DynamicArray* arr, int pos);

void Copy(DynamicArray sourceArray, DynamicArray* destination);
void interchange(DynamicArray* arr, int pos1, int pos2);