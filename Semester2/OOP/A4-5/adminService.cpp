#include "adminService.h"
#include <exception>
#include <assert.h>

AdminService::AdminService(Repository& repository) :
	repo{ repository }
{
}

void AdminService::AddMovie(std::string title, std::string genre, size_t year, size_t likes, std::string trailer)
{
	if (!Validator::ValidMovieAttributes(title, genre, year, likes, trailer))
		throw std::exception("Invalid movie parameters!!");

	Movie newMovie = Movie(title, genre, year, likes, trailer);

	bool duplicate = false;
	for (size_t i = 0; i < this->repo.GetSize(); ++i)
		if (this->repo[i] == newMovie) duplicate = true;

	if (duplicate) throw std::exception("A movie with the same name already exists!");

	this->repo.AddElement(newMovie);

}

void AdminService::RemoveMovie(std::string title)
{
	int pos = this->repo.FindElemByTitle(title);
	if (pos == -1)
		throw std::exception("Movie not found!");
	
	this->repo.RemoveElemnt(pos);
}

void AdminService::UpdateMovieTitle(std::string title, std::string newTitle)
{
	if (!Validator::ValidMovieTitle(newTitle)) throw std::exception("Invalid movie!");
	size_t pos = this->repo.FindElemByTitle(title);

	if (pos >= this->repo.GetSize()) throw std::exception("Movie not found!");
	this->repo.UpdateTitle(pos, newTitle);
}

void AdminService::UpdateMovieGenre(std::string title, std::string newGenre)
{
	if (!Validator::ValidMovieGenre(newGenre)) throw std::exception("Invalid movie!");
	size_t pos = this->repo.FindElemByTitle(title);

	if (pos >= this->repo.GetSize()) throw std::exception("Movie not found!");
	this->repo.UpdateGenre(pos, newGenre);
}

void AdminService::UpdateMovieYear(std::string title, size_t newYear)
{
	if (!Validator::ValidMovieYear(newYear)) throw std::exception("Invalid movie!");
	size_t pos = this->repo.FindElemByTitle(title);

	if (pos >= this->repo.GetSize()) throw std::exception("Movie not found!");
	this->repo.UpdateYear(pos, newYear);
}

void AdminService::UpdateMovieLikes(std::string title, size_t newLikes)
{
	if (!Validator::ValidMovieLikes(newLikes)) throw std::exception("Invalid movie!");
	size_t pos = this->repo.FindElemByTitle(title);

	if (pos >= this->repo.GetSize()) throw std::exception("Movie not found!");
	this->repo.UpdateLikes(pos, newLikes);
}

void AdminService::UpdateMovieTrailer(std::string title, std::string newTraier)
{
	size_t pos = this->repo.FindElemByTitle(title);

	if (pos >= this->repo.GetSize()) throw std::exception("Movie not found!");
	this->repo.UpdateTrailer(pos, newTraier);
}

Repository AdminService::GetRepo() const
{
	return this->repo;
}

void AdminService::InitializeRepo()
{
	AddMovie("Titanic", "Romance", 1997, 55000, "https://www.youtube.com/watch?v=kVrqfYjkTdQ");
	AddMovie("Sinister", "Horror", 2012, 2110, "https://www.youtube.com/watch?v=_kbQAJR9YWQ");
	AddMovie("Countdown", "Horror", 2019, 1000, "https://www.youtube.com/watch?v=TZsgNH17_X4");
	AddMovie("The notebook", "Romance", 2004, 3000, "https://www.youtube.com/watch?v=yDJIcYE32NU");
	AddMovie("Sinner", "Drama", 2017, 4000, "https://www.youtube.com/watch?v=ZEfnpFuzxnE");
	AddMovie("Anabelle", "Horror", 2020, 2993, "https://www.youtube.com/watch?v=paFgQNPGlsg");
	AddMovie("Dirty grandpa", "Comedy", 2016, 4002, "https://www.youtube.com/watch?v=aZSzMIFZT7Q");
	AddMovie("Nerve", "Action", 2016, 4563, "https://www.youtube.com/watch?v=2PR9MOPTI7g");
	AddMovie("Letters to Juliet", "Romance", 2010, 10024, "https://www.youtube.com/watch?v=8j0qMY-LeKM");
	AddMovie("Luca", "Family", 2021, 3000, "https://www.youtube.com/watch?v=mYfJxlgR2jw&t=61s");
}