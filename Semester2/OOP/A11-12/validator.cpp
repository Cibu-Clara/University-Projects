#include "validator.h"
#include "exception.h"
#include <string>

bool MovieValidator::validate(const Movie& m) {
    std::string errors = "";
    if (!ValidMovieTitle(m.GetTitle()))
    {
        errors += "The movie title must have at least two characters!\n";
    }
    if (!ValidMovieGenre(m.GetGenre()))
    {
        errors += "The movie genre must have at least two characters!\n";
    }
    if (!ValidMovieYear(m.GetYear()))
    {
        errors += "Invalid year of release!\n";
    }
    if (!ValidMovieLikes(m.GetLikes()))
    {
        errors += "Number of likes must be a natural number!\n";
    }
    if (errors.size() > 0)
    {
        throw ValidatorException(errors);
    }
}


bool MovieValidator::ValidMovieTitle(std::string title)
{
    return title.size() >= 2;
}

bool MovieValidator::ValidMovieGenre(std::string genre)
{
    return genre.size() >= 2;
}

bool MovieValidator::ValidMovieYear(size_t year)
{
    return year >= 1800 && year <= 2022;
}

bool MovieValidator::ValidMovieLikes(size_t likes)
{
    return likes >= 0;
}
