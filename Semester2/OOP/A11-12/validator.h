#pragma once
#include "domain.h"

class MovieValidator
{
public:
	static bool ValidMovieTitle(std::string title);
	static bool ValidMovieGenre(std::string genre);
	static bool ValidMovieYear(size_t year);
	static bool ValidMovieLikes(size_t likes);
	static bool validate(const Movie&);
};


