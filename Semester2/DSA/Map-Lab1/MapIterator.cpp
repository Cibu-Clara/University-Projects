#include "Map.h"
#include "MapIterator.h"
#include <exception>
using namespace std;


MapIterator::MapIterator(const Map& d) : map(d)
{	
	// Complexity: Theta(1)
	this->current = 0;
}

void MapIterator::first() {
	// Complexity: Theta(1)
	this->current = 0;
}

void MapIterator::next() {
	// Complexity: Theta(1)
	if (this->current == this->map.nrPairs)
		throw exception();
	else
		this->current++;
}

TElem MapIterator::getCurrent() {
	// Complexity: Theta(1)
	if (this->current == this->map.nrPairs)
		throw exception();
	return this->map.elements[this->current];
}

bool MapIterator::valid() const {
	// Complexity: Theta(1)
	if (this->current < this->map.nrPairs)
		return true;
	else 
		return false;
}



