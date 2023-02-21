#include "adminService.h"
#include "exception.h"
#include <assert.h>

AdminService::AdminService(FileRepository& repository) :
	repo{ repository }
{
}

void AdminService::AddMovie(std::string title, std::string genre, size_t year, size_t likes, std::string trailer)
{
	Movie newMovie = Movie(title, genre, year, likes, trailer);
	MovieValidator::validate(newMovie);
	this->repo.AddElement(newMovie);

}

void AdminService::RemoveMovie(std::string title)
{
	size_t pos = this->repo.FindElemByTitle(title);
	this->repo.RemoveElemnt(pos);
}

void AdminService::UpdateMovieTitle(std::string title, std::string newTitle)
{
	if (!MovieValidator::ValidMovieTitle(newTitle)) 
		throw ValidatorException("The movie title must have at least two characters!\n");
	size_t pos = this->repo.FindElemByTitle(title);
	this->repo.UpdateTitle(pos, newTitle);
}

void AdminService::UpdateMovieGenre(std::string title, std::string newGenre)
{
	if (!MovieValidator::ValidMovieGenre(newGenre)) 
		throw ValidatorException("The movie genre must have at least two characters!\n");
	size_t pos = this->repo.FindElemByTitle(title);
	this->repo.UpdateGenre(pos, newGenre);
}

void AdminService::UpdateMovieYear(std::string title, size_t newYear)
{
	if (!MovieValidator::ValidMovieYear(newYear))
		throw ValidatorException("Invalid year of release!\n");
	size_t pos = this->repo.FindElemByTitle(title);
	this->repo.UpdateYear(pos, newYear);
}

void AdminService::UpdateMovieLikes(std::string title, size_t newLikes)
{
	if (!MovieValidator::ValidMovieLikes(newLikes)) 
		throw ValidatorException("Number of likes must be a natural number!\n");
	size_t pos = this->repo.FindElemByTitle(title);
	this->repo.UpdateLikes(pos, newLikes);
}

void AdminService::UpdateMovieTrailer(std::string title, std::string newTraier)
{
	size_t pos = this->repo.FindElemByTitle(title);
	this->repo.UpdateTrailer(pos, newTraier);
}

FileRepository AdminService::GetRepo() const
{
	return this->repo;
}