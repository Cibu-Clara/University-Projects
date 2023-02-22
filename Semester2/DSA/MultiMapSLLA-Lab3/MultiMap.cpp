#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;

// Complexity: Theta(1)
MultiMap::MultiMap() {
	this->length = 0;
	this->firstEmpty = 0;
	this->head = -1;
	this->tail = -1;
	this->capacity = 3;
	this->next = new int[4];

	for (int i = 0; i < this->capacity; i++)
		this->next[i] = i + 1;

	this->next[this->capacity] = -1;

	this->map = new KeyNode[3];
}

// Complexity: Theta(1)
void MultiMap::init_keyNode(MultiMap::KeyNode& keynode)
{
	keynode.capacityInfoSLLA = 3;
	keynode.lengthInfoSLLA = 0;
	keynode.firstEmpty = 0;
	keynode.head = -1;
	keynode.tail = -1;
	keynode.next = new int[4];

	for (int i = 0; i < keynode.capacityInfoSLLA; i++)
		keynode.next[i] = i + 1;

	keynode.next[keynode.capacityInfoSLLA] = -1;

	keynode.infoSLLA = new TValue[3];
}

// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(n) 
	//		=> total: O(n) ; n = number of keys in the map
void MultiMap::add(TKey c, TValue v) {
	int position, value_position;

	if (this->length == 0)
	{
		this->init_keyNode(this->map[this->firstEmpty]);
		this->map[this->firstEmpty].key = c;
		this->map[this->firstEmpty].head = this->map[this->firstEmpty].firstEmpty;
		this->map[this->firstEmpty].tail = this->map[this->firstEmpty].head;
		this->map[this->firstEmpty].infoSLLA[this->map[this->firstEmpty].firstEmpty] = v;
		this->map[this->firstEmpty].firstEmpty = this->map[this->firstEmpty].next[this->map[this->firstEmpty].firstEmpty];
		this->map[this->firstEmpty].lengthInfoSLLA += 1;
		this->head = this->firstEmpty;
		this->tail = this->firstEmpty;
		this->firstEmpty = this->next[this->firstEmpty];
	}
	else
	{
		position = this->head;
		while (position != this->firstEmpty && this->map[position].key != c)
			position = this->next[position];

		//key not in the map
		if (position == this->firstEmpty)
		{
			// resize map
			if (this->next[this->firstEmpty] == -1)
			{
				int* new_next = new int[this->capacity * 2 + 1];
				KeyNode* new_map = new KeyNode[this->capacity * 2];

				for (int i = 0; i < this->capacity; i++)
				{
					new_next[i] = this->next[i];
					new_map[i] = this->map[i];
				}
				for (int i = this->capacity; i < this->capacity * 2; i++)
					new_next[i] = i + 1;
				new_next[this->capacity * 2] = -1;
				delete[]this->next;
				delete this->map;
				this->next = new_next;
				this->capacity = this->capacity * 2;
				this->map = new_map;
			}
			this->init_keyNode(this->map[this->firstEmpty]);
			this->map[this->firstEmpty].key = c;
			this->map[this->firstEmpty].head = this->map[this->firstEmpty].firstEmpty;
			this->map[this->firstEmpty].infoSLLA[this->map[this->firstEmpty].firstEmpty] = v;
			this->map[this->firstEmpty].firstEmpty = this->map[this->firstEmpty].next[this->map[this->firstEmpty].firstEmpty];
			this->map[this->firstEmpty].lengthInfoSLLA += 1;
			this->tail = this->firstEmpty;
			this->firstEmpty = this->next[this->firstEmpty];
		}
		else
		{
			// resize infoSLLA
			if (this->map[position].lengthInfoSLLA == this->map[position].capacityInfoSLLA)
			{
				int* new_infoSLLA_next = new int[this->map[position].capacityInfoSLLA * 2 + 1];
				TValue* new_infoSLLA = new TValue[this->map[position].capacityInfoSLLA * 2];
				for (int i = 0; i < this->map[position].capacityInfoSLLA; i++)
				{
					new_infoSLLA_next[i] = this->map[position].next[i];
					new_infoSLLA[i] = this->map[position].infoSLLA[i];
				}
				for (int i = this->map[position].capacityInfoSLLA; i < this->map[position].capacityInfoSLLA * 2; i++)
					new_infoSLLA_next[i] = i + 1;
				new_infoSLLA_next[this->map[position].capacityInfoSLLA * 2] = -1;
				delete[]this->map[position].next;
				this->map[position].next = new_infoSLLA_next;

				delete[]this->map[position].infoSLLA;
				this->map[position].infoSLLA = new_infoSLLA;
				this->map[position].capacityInfoSLLA *= 2;
			}
			this->map[position].infoSLLA[this->map[position].firstEmpty] = v;
			this->map[position].lengthInfoSLLA += 1;
			this->map[position].tail = this->map[position].firstEmpty;
			this->map[position].firstEmpty = this->map[position].next[this->map[position].firstEmpty];
		}
	}

	this->length += 1;
}

// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(n + m) 
	//		=> total: O(n + m) ; n = number of keys in the map, m = number of values in the SLLA of the key
bool MultiMap::remove(TKey c, TValue v) {
	int position, value_position, prev_position, prev_value_position;
	TValue null_value = NULL_TVALUE;

	if (this->length == 0)
		return false;

	prev_position = -2;
	position = this->head;

	while (position != this->firstEmpty && this->map[position].key != c)
	{
		prev_position = position;
		position = this->next[position];
	}

	if (position == this->firstEmpty)
		return false;

	value_position = this->map[position].head;
	prev_value_position = -2;

	while (value_position != this->map[position].firstEmpty && this->map[position].infoSLLA[value_position] != v)
	{
		prev_value_position = value_position;
		value_position = this->map[position].next[value_position];
	}

	if (value_position == this->map[position].firstEmpty)
		return false;
	
	this->map[position].lengthInfoSLLA -= 1;
	this->length -= 1;


	if (this->map[position].head == value_position)
	{
		if (this->map[position].lengthInfoSLLA == 0)
		{
			delete[]this->map[position].next;
			delete[]this->map[position].infoSLLA;
			this->map[position].head = -1;
			this->map[position].tail = -1;
			this->map[position].firstEmpty = value_position;

			if (position == this->head)
			{
				if (this->length == 0)
				{
					this->next[position] = this->firstEmpty;
					this->firstEmpty = position;
					this->head = -1;
					this->tail = -1;
				}
				else
				{
					this->head = this->next[position];
					this->next[position] = this->firstEmpty;
					this->next[this->tail] = position;
					this->firstEmpty = position;
				}
			}
			else
			{
				if (this->next[position] != this->firstEmpty)
				{
					this->next[prev_position] = this->next[position];
					this->next[this->tail] = position;
				}
				else
					this->tail = prev_position;

				this->next[position] = this->firstEmpty;
				this->firstEmpty = position;
			}
		}
		else
		{
			this->map[position].head = this->map[position].next[value_position];
			this->map[position].next[value_position] = this->map[position].firstEmpty;
			this->map[position].next[this->map[position].tail] = value_position;
			this->map[position].firstEmpty = value_position;
		}
	}
	else
	{
		if (this->map[position].next[value_position] != this->map[position].firstEmpty)
		{
			this->map[position].next[prev_value_position] = this->map[position].next[value_position];
			this->map[position].next[this->map[position].tail] = value_position;
		}
		else
			this->map[position].tail = prev_value_position;

		this->map[position].next[value_position] = this->map[position].firstEmpty;
		this->map[position].firstEmpty = value_position;
	}

	return true;
}

// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(n + m) 
	//		=> total: O(n + m) ; n = number of keys in the map, m = number of values in the SLLA of the key
vector<TValue> MultiMap::search(TKey c) const {
	int position, value_position;
	vector<TValue> elements;
	if (this->length == 0)
		return vector<TValue>();

	position = this->head;
	while (position != this->firstEmpty && this->map[position].key != c)
		position = this->next[position];

	if (position != this->firstEmpty)
	{
		value_position = this->map[position].head;
		while (value_position != this->map[position].firstEmpty)
		{
			elements.push_back(this->map[position].infoSLLA[value_position]);
			value_position = this->map[position].next[value_position];
		}
	}

	return elements;
}

// Complexity: Theta(1)
int MultiMap::size() const {
	return this->length;
}

// Complexity: Theta(1)
bool MultiMap::isEmpty() const {
	if (this->length == 0)
		return true;

	return false;
}

// Complexity:
	//		BC : Theta(1)
	//      WC : Theta(n) 
	//		=> total: O(n) ; n = number of keys in the map
void MultiMap::empty() {
	for (int i = this->head; i != this->firstEmpty && i != -1; i = this->next[i])
	{
		if (this->map[i].lengthInfoSLLA != 0)
		{
			delete[]this->map[i].infoSLLA;
			delete[]this->map[i].next;
		}
	}
	delete[]this->map;
	delete[]this->next;
	this->length = 0;
	this->firstEmpty = 0;
	this->head = -1;
	this->tail = -1;
	this->capacity = 3;
	this->next = new int[4];

	for (int i = 0; i < this->capacity; i++)
		this->next[i] = i + 1;

	this->next[this->capacity] = -1;

	this->map = new KeyNode[3];
}

// Complexity: Theta(1)
MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}

// Complexity: Theta(1)
MultiMap::~MultiMap() {
	for (int i = this->head; i != this->firstEmpty && i != -1; i = this->next[i])
	{
		if (this->map[i].lengthInfoSLLA != 0)
		{
			delete[]this->map[i].infoSLLA;
			delete[]this->map[i].next;
		}
	}
	delete[]this->map;
	delete[]this->next;
}

