#pragma once
#include "SortedSet.h"

class SortedSetIterator
{
	friend class SortedSet;
private:
	const SortedSet& multime;
	SortedSetIterator(const SortedSet& m);

	int currentNode = 0;
	int count = 0;
	int* Travers;
	int* ordered;
	int top = -1;

public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;
};

