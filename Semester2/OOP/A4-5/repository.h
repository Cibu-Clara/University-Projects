#pragma once
#include "domain.h"
#include "dynamicVector.h"
#include <string>
#include <iostream>

class Repository
{
public:
	/// <summary>
	/// Default constructor for movie repo
	/// </summary>
	Repository();

	/// <summary>
	/// Constructor which initialize the repo with a dynamic array of a given size
	/// </summary>
	/// <param name="maxSize">The capacity of the dynamic array</param>
	Repository(size_t maxSize);

	/// <summary>
	/// Copy constructor for a movie repo
	/// </summary>
	/// <param name="other">The repo to copy to</param>
	Repository(const Repository& other);

	/// <summary>
	/// Destructor for a movie repo
	/// </summary>
	~Repository();

	/// <summary>
	/// Copy a repo to another
	/// </summary>
	/// <param name="other">The repo to be copied</param>
	/// <returns>An object with the same elements as the existing one</returns>
	Repository& operator=(const Repository& other);

	/// <summary>
	/// Indexing operator for movie repository
	/// </summary>
	/// <param name="position">The position of the element to be returned</param>
	/// <returns>The element</returns>
	Movie& operator[](size_t position);

	/// <summary>
	/// Equality of two elements
	/// </summary>
	/// <param name="other">The object to compare to</param>
	/// <returns>true if they are the same, false otherwise</returns>
	bool operator==(const Repository& other) const;

	/// <summary>
	/// Insertion operator for the repository class
	/// </summary>
	/// <param name="os">The stream object to write to</param>
	/// <param name="repo">The repo to be written</param>
	/// <returns>A stream containing the elements of the repository</returns>
	friend std::ostream& operator<<(std::ostream& os, const Repository& repo);

	/// <summary>
	/// Adds a new movie to the repository
	/// </summary>
	/// <param name="movie">The movie to be added</param>
	void AddElement(const Movie& movie);

	/// <summary>
	/// Removes a movie from the repo
	/// </summary>
	/// <param name="position">The positio of the element to be removed</param>
	void RemoveElemnt(size_t position);

	/// <summary>
	/// Update the title of a movie
	/// </summary>
	/// <param name="position">The position of the movie to be changed</param>
	/// <param name="newTitle">The new title to be given to the movie</param>
	void UpdateTitle(size_t position, std::string newTitle);

	/// <summary>
	/// Update the genre of a movie
	/// </summary>
	/// <param name="position">The position of the movie to be changed</param>
	/// <param name="newGenre">The new genre to be given to the movie</param>
	void UpdateGenre(size_t position, std::string newGenre);

	/// <summary>
	/// Update the year the movie has been released 
	/// </summary>
	/// <param name="position">The position of the movie to be updated</param>
	/// <param name="newYear">The new year to be given to the movie</param>
	void UpdateYear(size_t position, size_t newYear);

	/// <summary>
	/// Update the number of likes of a movie
	/// </summary>
	/// <param name="position">The postition of the movie to be updated</param>
	/// <param name="newLikes">The new number of likes to be given the movie</param>
	void UpdateLikes(size_t position, size_t newLikes);

	/// <summary>
	/// Update the trailer of a movie
	/// </summary>
	/// <param name="position">The position of the movie to be updated</param>
	/// <param name="newTraier">THe new trailer to be given to a certain movie</param>
	void UpdateTrailer(size_t position, std::string newTraier);

	/// <summary>
	/// Find the position of the element which we want to search for
	/// </summary>
	/// <param name="title">The title of the movie to be removed</param>
	/// <returns>The position of the movie</returns>
	size_t FindElemByTitle(std::string title) const;

	size_t GetSize() const;
	DynamicVector<Movie> GetArray() const;
	friend class Tests;

private:
	DynamicVector<Movie>* elementsArray;
};