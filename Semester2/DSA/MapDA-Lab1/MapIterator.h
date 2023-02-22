#pragma once
#include "Map.h"

class MapIterator
{
	//DO NOT CHANGE THIS PART
	friend class Map;
private:
	const Map& map;
	int current; // the position of the current element from the array of the map

	MapIterator(const Map& m);
public:
	// resets your iterator to the beginning of the container
	void first();

	// moves the iterator in the container
	// throws an exception if the iterator is not valid
	void next();

	// returns the value of the element from the current position
	// throws an exception if the iterator is not valid
	TElem getCurrent();

	// checks if the iterator is valid or not
	bool valid() const;
};


