#pragma once

typedef int TElem;
typedef TElem TComp;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TELEM -11111
#define NULL_POS -9999999
class SortedSetIterator;

struct BST {
	TElem* info;
	int* left;
	int* right;
	int* parent;
	int firstEmpty, capacity, root;
};

class SortedSet {
	friend class SortedSetIterator;
private:
	int setSize;
	Relation relation;
	BST bst;

	void resize();
	int getMinimum(int node) const;

public:
	//constructor
	explicit SortedSet(Relation r);

	//adds an element to the sorted set
	//if the element was added, the operation returns true, otherwise (if the element was already in the set) 
	//it returns false
	bool add(TComp e);
	
	//removes an element from the sorted set
	//if the element was removed, it returns true, otherwise false
	bool remove(TComp e);

	//checks if an element is in the sorted set
	bool search(TComp elem) const;

	//returns the number of elements from the sorted set
	int size() const;

	//checks if the sorted set is empty
	bool isEmpty() const;

	// adds all elements of s into the SortedSet
	void unionn(const SortedSet& s);

	//returns an iterator for the sorted set
	SortedSetIterator iterator() const;

	// destructor
	~SortedSet();
};
