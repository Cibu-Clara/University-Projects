#pragma once
#include <iostream>
#include <string>

class Movie
{
public:
	/// <summary>
	/// Default constructor for Movie class
	/// </summary>
	Movie();

	/// <summary>
	/// Constructor for the movie class with initialization for all fields
	/// </summary>
	/// <param name="title">The title of the movie</param>
	/// <param name="genre">The genre of the movie</param>
	/// <param name="year">The year the movie has been released</param>
	/// <param name="likes">The number of likes of the movie</param>
	/// <param name="trailer">A link to the movie trailer</param>
	Movie(std::string title, std::string genre, size_t year, size_t likes, std::string trailer);

	/// <summary>
	/// Destructor for the movie
	/// </summary>
	~Movie();

	//Getters
	std::string GetTitle() const;
	std::string GetGenre() const;
	size_t GetYear() const;
	size_t GetLikes() const;
	std::string GetTrailer() const;

	//Setters
	void SetTitle(std::string newTitle);
	void SetGenre(std::string newGenre);
	void SetYear(size_t newYear);
	void SetLikes(size_t newLikes);
	void SetTrailer(std::string newTrailer);

	/// <summary>
	/// Equality operator for the movie class
	/// </summary>
	/// <param name="other">The movie to compare the current obj with</param>
	/// <returns>True if the movies are identical, False otherwise</returns>
	bool operator==(const Movie& other) const;

	/// <summary>
	/// Insertion operator for the movie class
	/// </summary>
	/// <param name="os">The stream obj to write data of the movie to</param>
	/// <param name="movie">The movie whose fields will be written</param>
	/// <returns>A stream object which contains the data of the movie</returns>

	friend std::ostream& operator<<(std::ostream& os, const Movie& movie);
	friend std::istream& operator>>(std::istream& is, Movie& movie);
private:

	std::string title;
	std::string genre;
	size_t year;
	size_t likes;
	std::string trailer;
};


