#pragma once
#include "exception.h"

template <typename T>
class DynamicVector
{
private:
	// resizes the current dynamic vector, multiplying its capacity by a given factor (real number).
	void resize(double factor = 2);

	T* elems;
	int size;
	int capacity;
public:
	// default constructor for a dynamic vector 
	DynamicVector(int capacity = 1);

	// copy constructor for a dynamic vector
	DynamicVector(const DynamicVector& v);

	// assignment operator for a dynamic vector
	DynamicVector& operator=(const DynamicVector& v);

	/*	Overloading the subscript operator
		Input: pos - a valid position within the vector.
		Output: a reference to the element on position pos.
	*/
	T& operator[](int pos) const;

	// adds an element to the current dynamic vector.
	void addElem(const T& elem);

	// removes an element from the current dynamic vector
	void removeElem(int pos);

	// size getter & setter
	int getSize() const;
	void setSize(int s) { size = s; }

	// destructor for the dynamic vector
	~DynamicVector();
};

template <typename T>
DynamicVector<T>::DynamicVector(int capacity)
{
	this->size = 0;
	this->capacity = capacity;
	this->elems = new T[capacity];
}

template <typename T>
DynamicVector<T>::DynamicVector(const DynamicVector<T>& v)
{
	this->size = v.size;
	this->capacity = v.capacity;
	this->elems = new T[this->capacity];
	for (int i = 0; i < this->size; i++)
		this->elems[i] = v.elems[i];
}

template <typename T>
DynamicVector<T>& DynamicVector<T>::operator=(const DynamicVector<T>& v)
{
	if (this == &v)
		return *this;

	this->size = v.size;
	this->capacity = v.capacity;

	delete[] this->elems;
	this->elems = new T[this->capacity];
	for (int i = 0; i < this->size; i++)
		this->elems[i] = v.elems[i];

	return *this;
}

template<typename T>
T& DynamicVector<T>::operator[](int pos) const
{
	if (pos > this->size) 
		throw RepoException("");

	return this->elems[pos];
}

template <typename T>
void DynamicVector<T>::addElem(const T& elem)
{	
	if (this->size == this->capacity)
		this->resize();
	this->elems[this->size] = elem;
	this->size++;
}

template<typename T>
void DynamicVector<T>::removeElem(int pos)
{	
	if (pos == -1) return;

	for (int i = pos; i < this->size - 1; ++i)
		this->elems[i] = this->elems[i + 1];

	this->size--;
}

template <typename T>
void DynamicVector<T>::resize(double factor)
{
	this->capacity *= static_cast<int>(factor);

	T* newArray = new T[this->capacity];
	for (int i = 0; i < this->size; i++)
		newArray[i] = this->elems[i];

	delete[] this->elems;
	this->elems = newArray;
}

template <typename T>
int DynamicVector<T>::getSize() const
{
	return this->size;
}

template <typename T>
DynamicVector<T>::~DynamicVector()
{
	delete[] this->elems;
}