#include "validator.h"
#include <regex>

bool Validator::ValidMovieAttributes(std::string title, std::string genre, size_t year, size_t likes, std::string trailer)
{
	return ValidMovieTitle(title) && ValidMovieGenre(genre) && ValidMovieYear(year) && ValidMovieLikes(likes);
}

bool Validator::ValidMovieTitle(std::string title)
{
	return !title.empty();
}

bool Validator::ValidMovieGenre(std::string genre)
{
	return !genre.empty();
}

bool Validator::ValidMovieYear(size_t year)
{
	return year >= 1800 && year <= 2022;
}

bool Validator::ValidMovieLikes(size_t likes)
{
	return likes >= 0;
}
