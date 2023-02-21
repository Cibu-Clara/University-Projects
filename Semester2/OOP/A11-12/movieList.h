#pragma once
#include <vector>
#include "domain.h"

class MovieList
{
public:
	MovieList();
	void AddMovie(const Movie& movie);
	const std::vector<Movie>& GetList() const;
private:
	std::vector<Movie> movieList;
};

class MovieListWriter
{
public:
	virtual void WriteToFile(const MovieList& movieList) = 0;

	const std::string& GetFileName() const;
	void SetFileName(const std::string& newFile);
protected:
	std::string fileName;
};

class CSVMovieListWriter : 
	public MovieListWriter
{
public:
	CSVMovieListWriter(const std::string& fileName);
	void WriteToFile(const MovieList& movie_list) override;
};

class HTMLMovieListWriter :
	public MovieListWriter
{
public:
	HTMLMovieListWriter(const std::string& fileName);
	void WriteToFile(const MovieList& movie_list) override;
};



