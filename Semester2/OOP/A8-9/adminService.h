#pragma once
#include "fileRepository.h"
#include "validator.h"

class AdminService
{
public:
	/// <summary>
	/// Constructor for the admin service
	/// </summary>
	/// <param name="repo">The repository on which the class service operates</param>
	AdminService(FileRepository& repo);

	/// <summary>
	/// Adds a new movie to the repository
	/// </summary>
	/// <param name="title">The title of the movie</param>
	/// <param name="genre">The genre of the movie</param>
	/// <param name="year">The year the move has been released</param>
	/// <param name="likes">The number of appreciations for the movie</param>
	/// <param name="trailer">The trailer of the movie</param>
	void AddMovie(std::string title, std::string genre, size_t year, size_t likes, std::string trailer);

	/// <summary>
	/// Remove a certain movie from the repository
	/// </summary>
	/// <param name="name">The name of the movie to be removed</param>
	void RemoveMovie(std::string name);

	/// <summary>
	/// Updates the title of a movie from the repository
	/// </summary>
	/// <param name="title">The title of the movie to be updated</param>
	/// <param name="newTitle">The new title to be given to the movie</param>
	void UpdateMovieTitle(std::string title, std::string newTitle);

	/// <summary>
	/// Updates the genre of a movie from the repo
	/// </summary>
	/// <param name="name">The name of the movie to update</param>
	/// <param name="newGenre">The new genre to be given to a movie</param>
	void UpdateMovieGenre(std::string name, std::string newGenre);

	/// <summary>
	/// Updates the year a movie has been released
	/// </summary>
	/// <param name="title"></param>
	/// <param name="newYear"></param>
	void UpdateMovieYear(std::string title, size_t newYear);

	/// <summary>
	/// Updates the number of likes a movie has been given
	/// </summary>
	/// <param name="name">The name of the movie to be updated</param>
	/// <param name="newLikes">The new number of likes to be given to a movie</param>
	void UpdateMovieLikes(std::string name, size_t newLikes);

	/// <summary>
	/// Update the trailer associated to a given movie
	/// </summary>
	/// <param name="name">The name of the movie to be updated</param>
	/// <param name="newTraier">The new trailer to be attributed to the movie</param>
	void UpdateMovieTrailer(std::string name, std::string newTraier);

	/// <summary>
	/// Prints the content of the repo
	/// </summary>
	/// <returns></returns>
	FileRepository GetRepo() const;

	/// <summary>
	/// Adds 10 entries to the repo
	/// </summary>
	// void InitializeRepo();
	friend class Tests;
private:

	FileRepository& repo;
};
