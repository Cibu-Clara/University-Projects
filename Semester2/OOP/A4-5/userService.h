#pragma once
#include "repository.h"

class UserService
{
public:

	/// <summary>
	/// Constructor for the user servivce
	/// </summary>
	/// <param name="repo">The repository the service depends on</param>
	UserService(Repository& repo);

	/// <summary>
	/// Add an object of type Movie to the watch list
	/// </summary>
	/// <param name="movie">The movie wwe want to add to the watch list</param>
	void AddToWatchList(const Movie& movie);

	/// <summary>
	/// Move the index to the net movie in the list
	/// </summary>
	void GoToNextMovie();

	/// <summary>
	/// Filter the watch list by a given genre
	/// </summary>
	/// <param name="genre">The genre too filter by</param>
	void FilterByGenre(std::string genre);

	/// <summary>
	/// Deletes an obj of type movie from the watch list after seeing it
	/// </summary>
	/// <param name="movie">The movie to delete</param>
	/// /// <param name="likes">After the movie has been deleted, the user has the opportunity to rate the movie</param>
	void DeleteMovieWatchList(std::string movie, size_t likes);

	/// <summary>
	/// Set the index to point to the beginning of the array
	/// </summary>
	void ReinitializeMovieList();

	//Getters
	DynamicVector<Movie> GetMovieList() const;

	Movie& GetCurrentMovie() const;
	friend class Tests;

private:
	Repository& repo;
	DynamicVector<Movie> CurrentList;
	DynamicVector<Movie> WatchList;
	size_t index;
};

