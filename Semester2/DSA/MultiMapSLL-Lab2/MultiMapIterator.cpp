#include <stdexcept>
#include "MultiMapIterator.h"
#include "MultiMap.h"

//Complexity: Theta(1)
MultiMapIterator::MultiMapIterator(const MultiMap& c) : col(c) {
	this->current_node = c.head;
}

//Complexity: Theta(1)
TElem MultiMapIterator::getCurrent() const {
	if (this->current_node == nullptr)
		throw std::runtime_error("Iterator getCurrent error!");
	TElem result = this->current_node->data;
	return result;
}

//Complexity: Theta(1)
bool MultiMapIterator::valid() const {
	return this->current_node != nullptr;
}

//Complexity: Theta(1)
void MultiMapIterator::next() {
	if (this->current_node == nullptr)
		throw std::runtime_error("Iterator next error!");
	this->current_node = this->current_node->next;
}

//Complexity: Theta(1)
void MultiMapIterator::first() {
	this->current_node = this->col.head;
}
