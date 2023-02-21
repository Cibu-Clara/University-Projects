#include "repository.h"
#include <sstream>
#include "exception.h"

Repository::Repository() 
{
}

Repository::~Repository()
{
}

void Repository::AddElement(const Movie& movie)
{
	bool duplicate = false;
	for (size_t i = 0; i < GetSize(); ++i)
		if (GetMovies()[i] == movie) duplicate = true;
	if (duplicate) 
		throw RepoException("A movie with the same name already exists!\n");
	this->movies.push_back(movie);
}

void Repository::RemoveElemnt(size_t position)
{
	if (position >= GetSize())
		throw RepoException("Movie not found!\n");
	this->movies.erase(this->movies.begin() + position);
}

void Repository::UpdateTitle(size_t position, std::string newTitle)
{
	if (position >= GetSize())
		throw RepoException("Movie not found!\n");
	this->movies[position].SetTitle(newTitle);
}

void Repository::UpdateGenre(size_t position, std::string newGenre)
{
	if (position >= GetSize())
		throw RepoException("Movie not found!\n");
	this->movies[position].SetGenre(newGenre);
}

void Repository::UpdateYear(size_t position, size_t newYear)
{
	if (position >= GetSize())
		throw RepoException("Movie not found!\n");
	this->movies[position].SetYear(newYear);
}

void Repository::UpdateLikes(size_t position, size_t newLikes)
{
	if (position >= GetSize())
		throw RepoException("Movie not found!\n");
	this->movies[position].SetLikes(newLikes);
}

void Repository::UpdateTrailer(size_t position, std::string newTrailer)
{
	if (position >= GetSize())
		throw RepoException("Movie not found!\n");
	this->movies[position].SetTrailer(newTrailer);
}

size_t Repository::FindElemByTitle(std::string title) const
{
	auto it = find_if(this->movies.begin(), this->movies.end(), [title](Movie m) {return m.GetTitle() == title; });
	if (it == this->movies.end()) return -1;

	return it - this->movies.begin();
}

size_t Repository::GetSize() const
{
	return this->movies.size();
}

std::vector<Movie> Repository::GetMovies() const
{
	return this->movies;
}