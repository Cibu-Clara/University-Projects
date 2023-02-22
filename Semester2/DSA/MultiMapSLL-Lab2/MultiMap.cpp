#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;


MultiMap::MultiMap() {
	this->head = nullptr;
	this->nr_elems = 0;
}


void MultiMap::add(TKey c, TValue v) {
	SLLKey* key_sll = this->getKey(c);
	SLLNode* new_node = new SLLNode[1];
	new_node->value = v;
	new_node->next = nullptr;
	this->nr_elems++;
	//if the key is already in the SLL
	if (key_sll != nullptr) {
		new_node->next = key_sll->head;
		key_sll->head = new_node;
		//if the key is not in the SLL
	}
	else {
		SLLKey* new_key = new SLLKey[1];
		new_key->key = c;
		new_key->next = this->head;
		this->head = new_key;
		new_key->head = new_node;
	}
}


bool MultiMap::remove(TKey c, TValue v) {
	//TODO - Implementation
	return  false;
}


vector<TValue> MultiMap::search(TKey c) const {
	//TODO - Implementation
	return vector<TValue>();
}


int MultiMap::size() const {
	//TODO - Implementation
	return 0;
}


bool MultiMap::isEmpty() const {
	//TODO - Implementation
	return false;
}

MultiMapIterator MultiMap::iterator() const {
	return MultiMapIterator(*this);
}


MultiMap::~MultiMap() {
	//TODO - Implementation
}

