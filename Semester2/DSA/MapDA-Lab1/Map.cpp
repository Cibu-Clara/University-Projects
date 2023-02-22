#include "Map.h"
#include "MapIterator.h"
#include <vector>

Map::Map() {
	// Complexity: Theta(1)
	// we start with the initial capacity of 1, the resize being made by doubling it
	this->capacity = 1;

	// the initial number of elements in the container is 0
	this->nrPairs = 0;

	this->elements = new TElem[this->capacity];
}

Map::~Map() {
	// Complexity: Theta(1)
	delete[] this->elements;
}

TValue Map::add(TKey c, TValue v) {
	// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(nrPairs) + Theta(nrPairs) = Theta(nrPairs)
	//		=> total: O(nrPairs)
	// if we don't have enough space to introduce new values, we resize the capacity 
	if (nrPairs >= capacity) {
		this->capacity *= 2; // double the capacity
		TElem* new_elements = new TElem[this->capacity]; // we copy the elements in the new memory space
		for (int i = 0; i < this->nrPairs; i++)
			new_elements[i] = this->elements[i];
		delete[] this->elements; // we free the memory
		this->elements = new_elements; // we move the elements back to the initial place
	}

	// check if the key already exists in the map
	for (int i = 0; i < this->nrPairs; i++) {
		if (this->elements[i].first == c) {
			TValue aux = elements[i].second;
			this->elements[i].second = v;
			return aux;
		}
	}

	this->elements[this->nrPairs].first = c;
	this->elements[this->nrPairs].second = v;
	this->nrPairs++;
	return NULL_TVALUE;
}

TValue Map::search(TKey c) const {
	// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(nrPairs)
	//		=> total: O(nrPairs)
	for (int i = 0; i < this->nrPairs; i++) {
		if (this->elements[i].first == c)
			return this->elements[i].second;
	}
	return NULL_TVALUE;
}

TValue Map::remove(TKey c) {
	// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(nrPairs)
	//		=> total: O(nrPairs)
	for (int i = 0; i < this->nrPairs; i++)
		if (this->elements[i].first == c)
		{	
			TValue aux = this->elements[i].second;
			this->elements[i] = this->elements[this->nrPairs - 1];
			this->nrPairs--;
			return aux;
		}
	return NULL_TVALUE;
}

int Map::size() const {
	// Complexity: Theta(1)
	return this->nrPairs;
}

bool Map::isEmpty() const {
	// Complexity: Theta(1)
	if (this->nrPairs == 0)
		return true;
	else
		return false;
}

vector<TValue> Map::valueMap() const
{	
	// Complexity: Theta(nrPairs)
	vector<TValue> vect;
	for (int i = 0; i < this->nrPairs; i++)
		vect.push_back(this->elements[i].second);

	return vect;
}

MapIterator Map::iterator() const {
	// Complexity: Theta(1)
	return MapIterator(*this);
}



