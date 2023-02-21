#include "DynamicArray.h"
#include <stdlib.h>
#include <assert.h>

DynamicArray* createDynamicArray(int capacity)
{
	DynamicArray* da = (DynamicArray*) malloc(sizeof(DynamicArray));
	// make sure that the space was allocated
	if (da == NULL)
		return NULL;

	da->capacity = capacity;
	da->length = 0;

	// allocate space for the elements
	da->elems = (TElement*) malloc(capacity * sizeof(TElement));
	if (da->elems == NULL)
	{
		free(da->elems);
		return NULL;
	}

	return da;
}

void destroy(DynamicArray* arr)
{
	if (arr == NULL)
		return;

	// free the space allocated for the elements
	free(arr->elems);
	arr->elems = NULL;

	// free the space allocated for the dynamic array
	free(arr);
	arr = NULL;
}

// Resizes the array, allocating more space.
// If more space cannot be allocated, returns -1, else it returns 0.
int resize(DynamicArray* arr)
{
	if (arr == NULL)
		return -1;

	arr->capacity *= 2;

	// allocate new memory, copy everything and deallocate the old memory
	TElement* aux = (TElement*)malloc(arr->capacity * sizeof(TElement));
	if (aux == NULL)
		return -1;
	for (int i = 0; i < arr->length; i++)
		aux[i] = arr->elems[i];
	free(arr->elems);
	arr->elems = aux;

	return 0;
}

void DuplicateArray(DynamicArray source, DynamicArray* destination)
{
	TElement* ptr = realloc(destination->elems, source.capacity * sizeof(TElement));
	destination->elems = ptr;
	if (destination->elems == NULL)
	{
		destination->capacity = 0;
		destination->length = 0;
	}

	Copy(source, destination);
}

void add(DynamicArray* arr, TElement t)
{
	if (arr == NULL)
		return;
	if (arr->elems == NULL)
		return;

	// resize the array, if necessary
	if (arr->length == arr->capacity)
		resize(arr);
	arr->elems[arr->length++] = t;
}

void del(DynamicArray* arr, int pos)
{
	if (arr == NULL || pos < 0 || pos >= arr->length)
		return;

	// !!! Do this only if we delete any element but the last
	if (pos != arr->length - 1)
		arr->elems[pos] = arr->elems[arr->length - 1];
	arr->length--;

}

int getLength(DynamicArray* arr)
{
	if (arr == NULL)
		return -1;

	return arr->length;
}

TElement getElem(DynamicArray* arr, int pos)
{
	return arr->elems[pos];
}


void Copy(DynamicArray sourceArray, DynamicArray* destinationArray)
{
	destinationArray->capacity = sourceArray.capacity;
	destinationArray->length = sourceArray.length;

	for (size_t i = 0; i < sourceArray.length; ++i)
		assign(sourceArray.elems[i], &destinationArray->elems[i]);
}

void interchange(DynamicArray* arr, int pos1, int pos2)
{
	Product prod = arr->elems[pos1];
	arr->elems[pos1] = arr->elems[pos2];
	arr->elems[pos2] = prod;
}