#include "MultiMap.h"
#include "MultiMapIterator.h"
#include <exception>
#include <iostream>

using namespace std;

// Complexity: Theta(1)
MultiMap::MultiMap() {
    this->head = nullptr;
    this->nr_elems = 0;
}

// Complexity: Theta(1)
void MultiMap::add(TKey c, TValue v) {
    SLLNode* new_node = new SLLNode[1];
    new_node->data = std::make_pair(c, v);
    new_node->next = nullptr;
    if (this->head == nullptr)
    {
        this->head = new_node;
    }
    else
    {
        new_node->next = this->head;
        this->head = new_node;
    }
    this->nr_elems++;
}

// Complexity:
    //		BC : Theta(1)
    //      WC : Theta(nr_elems) 
    //		=> total: O(nr_elems)
bool MultiMap::remove(TKey c, TValue v) {
    SLLNode* current = this->head;
    SLLNode* prev = nullptr;
    if (current == nullptr)
        return false;
    if (current->data.first == c && current->data.second == v) {
        this->head = current->next;
        delete current;
        this->nr_elems--;
        return true;
    }
    prev = current;
    current = current->next;
    while (current != nullptr)
    {
        if (current->data.first == c && current->data.second == v)
        {
            prev->next = current->next;
            delete current;
            this->nr_elems--;
            return true;
        }
        prev = current;
        current = current->next;
    }
    return false;
}

// Complexity:
    //		BC : Theta(1)
    //      WC : Theta(nr_elems) 
    //		=> total: O(nr_elems)
vector<TValue> MultiMap::search(TKey c) const {
    SLLNode* current = this->head;
    vector<TValue> result;
    while (current != nullptr)
    {
        if (current->data.first == c)
        {
            result.push_back(current->data.first);
        }
        current = current->next;
    }
    return result;
}

// Complexity: Theta(1) - we keep track of the number of values in the list
int MultiMap::size() const {
    return this->nr_elems;
}

// Complexity: Theta(1)
bool MultiMap::isEmpty() const {
    return this->head == nullptr;
}

// Complexity:
void MultiMap::empty()
{
    SLLNode* current = this->head;
    SLLNode* prev = nullptr;
    while (current != nullptr)
    {
        prev = current;
        current = current->next;
        delete prev;
    }
    this->head = nullptr;
    this->nr_elems = 0;

}

// Complexity: Theta(1)
MultiMapIterator MultiMap::iterator() const {
    return MultiMapIterator(*this);
}

// Complexity:
    //		BC : Theta(1)
    //      WC : Theta(nr_elems) 
    //		=> total: O(nr_elems)
MultiMap::~MultiMap() {
    SLLNode* current = this->head;
    SLLNode* prev = nullptr;
    while (current != nullptr)
    {
        prev = current;
        current = current->next;
        delete prev;
    }
}
