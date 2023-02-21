#pragma once
#include "domain.h"
#include <vector>
#include <string>
#include <iostream>

class Repository
{
private:
	std::vector<Movie> movies;
public:
	/// <summary>
	/// Default constructor for movie repo
	/// </summary>
	Repository();

	/// <summary>
	/// Destructor for a movie repo
	/// </summary>
	~Repository();

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

	std::vector<Movie> GetMovies() const;

	friend class Tests;
};