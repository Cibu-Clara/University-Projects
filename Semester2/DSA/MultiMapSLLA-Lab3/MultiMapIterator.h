#pragma once
#include "MultiMap.h"

class MultiMap;

class MultiMapIterator
{
	friend class MultiMap;

private:

	const MultiMap& col;
	int currentKey; // position of the currentKey in the map
	int currentElement; //position of the currentElement

	MultiMapIterator(const MultiMap& c);

public:
	TElem getCurrent()const;
	bool valid() const;
	void next();
	void first();
};

