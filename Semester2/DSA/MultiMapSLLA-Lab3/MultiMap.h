#pragma once
#include<vector>
#include<utility>

using namespace std;

typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -111111
#define NULL_TELEM pair<int,int>(-111111, -111111)
class MultiMapIterator;

class MultiMap
{
	friend class MultiMapIterator;

private:
	struct KeyNode {
		TValue* infoSLLA;
		int* next;
		int lengthInfoSLLA;
		int capacityInfoSLLA;
		int firstEmpty;
		int head;
		int tail;
		TKey key;
	};

	KeyNode* map;
	int* next;
	int length;
	int capacity;
	int firstEmpty;
	int head;
	int tail;

public:
	//constructor
	MultiMap();

	void init_keyNode(MultiMap::KeyNode& infoSLLA);

	//adds a key value pair to the multimap
	void add(TKey c, TValue v);

	//removes a key value pair from the multimap
	//returns true if the pair was removed (if it was in the multimap) and false otherwise
	bool remove(TKey c, TValue v);

	//returns the vector of values associated to a key. If the key is not in the MultiMap, the vector is empty
	vector<TValue> search(TKey c) const;

	//returns the number of pairs from the multimap
	int size() const;

	//checks whether the multimap is empty
	bool isEmpty() const;

	void empty();

	//returns an iterator for the multimap
	MultiMapIterator iterator() const;

	//descturctor
	~MultiMap();
};

