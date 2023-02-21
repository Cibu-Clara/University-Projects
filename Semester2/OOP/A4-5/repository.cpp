#include "repository.h"
#include <sstream>
#include <assert.h>

Repository::Repository() :
	elementsArray{ nullptr }
{
}

Repository::Repository(size_t maxSize) :
	elementsArray{ new DynamicVector<Movie>(maxSize) }
{
}

Repository::Repository(const Repository& other) :
	elementsArray{ new DynamicVector<Movie>(*other.elementsArray) }
{
}

Repository::~Repository()
{
	delete this->elementsArray;
	this->elementsArray = nullptr;
}

Repository& Repository::operator=(const Repository& other)
{
	if (this == &other) return *this;

	if (this->elementsArray == nullptr) this->elementsArray = new DynamicVector<Movie>();
	*this->elementsArray = *other.elementsArray;

	return *this;
}

Movie& Repository:: operator[](size_t position)
{
	return (*this->elementsArray)[position];
}

bool Repository::operator==(const Repository& other) const
{
	return *this->elementsArray == *other.elementsArray;
}

std::ostream& operator<<(std::ostream& os, const Repository& repo)
{
	os << *repo.elementsArray;
	return os;
}

void Repository::AddElement(const Movie& movie)
{
	*this->elementsArray + movie;
}

void Repository::RemoveElemnt(size_t position)
{
	this->elementsArray->RemoveElement(position);
}

void Repository::UpdateTitle(size_t position, std::string newTitle)
{
	(*this->elementsArray)[position].SetTitle(newTitle);
}

void Repository::UpdateGenre(size_t position, std::string newGenre)
{
	(*this->elementsArray)[position].SetGenre(newGenre);
}

void Repository::UpdateYear(size_t position, size_t newYear)
{
	(*this->elementsArray)[position].SetYear(newYear);
}

void Repository::UpdateLikes(size_t position, size_t newLikes)
{
	(*this->elementsArray)[position].SetLikes(newLikes);
}

void Repository::UpdateTrailer(size_t position, std::string newTrailer)
{
	(*this->elementsArray)[position].SetTrailer(newTrailer);
}

size_t Repository::FindElemByTitle(std::string title) const
{
	int pos = -1;
	for (int i = 0; i < this->GetSize(); ++i)
		if ((*this->elementsArray)[i].GetTitle() == title)
		{
			pos = i;
			break;
		}
	return pos;
}

size_t Repository::GetSize() const
{
	return this->elementsArray->GetSize();
}

DynamicVector<Movie> Repository::GetArray() const
{
	return *this->elementsArray;
}