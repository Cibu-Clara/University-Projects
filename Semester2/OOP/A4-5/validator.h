#pragma once
#include "domain.h"

class Validator
{
public:
	static bool ValidMovieAttributes(std::string titile, std::string genre, size_t year, size_t likes, std::string trailer);
	static bool ValidMovieTitle(std::string title);
	static bool ValidMovieGenre(std::string genre);
	static bool ValidMovieYear(size_t year);
	static bool ValidMovieLikes(size_t likes);
private:

};

