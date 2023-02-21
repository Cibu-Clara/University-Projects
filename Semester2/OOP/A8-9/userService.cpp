#include "userService.h"
#include "exception.h"
#include <iostream>

UserService::UserService(FileRepository& repo) :
	repo{ repo }, CurrentList{ repo.GetMovies() }, writer{ writer }, WatchList{ std::vector < Movie >(1) }, index{ 0 }
{
}

UserService::~UserService()
{
	delete writer;
}

void UserService::WriteData()
{
	writer->WriteToFile(movieList);
}

void UserService::AddToWatchList(const Movie& movie)
{
	/*bool found = false;

	if (this->WatchList.size() > 1)
	{
		auto it = find_if(this->WatchList.begin(), this->WatchList.end(), [movie](Movie m) {return m == movie; });
		if (it != this->WatchList.end()) found = true;
	}

	if (found) 
		throw RepoException("The movie has already been added to the watch list!\n");
	WatchList.push_back(movie);*/
	movieList.AddMovie(movie);
	GoToNextMovie();
}

void UserService::GoToNextMovie()
{
	if (index == CurrentList.size() - 1)
	{
		index = 0;
		return;
	}
	index++;
}

bool UserService::FilterByGenre(std::string genre)
{
	int i = 0;

	if (genre != "null")
		while (i != CurrentList.size())
		{
			if (CurrentList[i].GetGenre() != genre)
				CurrentList.erase(CurrentList.begin() + i), i--;
			i++;
		}
	if (CurrentList.size() == 0)
		return false;
	index = 0;
	return true;
}

void UserService::DeleteMovieWatchList(std::string movie, size_t likes)
{
	bool found = false;
	for (size_t i = 0; i < WatchList.size() && !found; i++)
		if (WatchList[i].GetTitle() == movie)
		{
			found = true;
			if (likes > 0)
			{
				size_t currentLikes = WatchList[i].GetLikes() + likes;
				bool found1 = false;
				for (size_t j = 0; j < CurrentList.size() && !found1; j++)
					if (CurrentList[j].GetTitle() == movie)
					{
						found1 = true;
						repo.UpdateLikes(j, currentLikes);
						CurrentList[j].SetLikes(currentLikes);
					}
			}

			WatchList.erase(WatchList.begin() + i);
		}
	if (!found) 
		throw RepoException("The movie you want to remove does not exist!\n");
}

void UserService::ReinitializeMovieList()
{
	if (CurrentList == repo.GetMovies())
		return;

	CurrentList = repo.GetMovies();
	index = 0;
}

std::vector<Movie> UserService::GetMovieList() const
{
	return WatchList;
}

Movie UserService::GetCurrentMovie() const
{
	return CurrentList[index];
}

const std::string& UserService::GetFileName() const
{
	return writer->GetFileName();
}

const std::string& UserService::GetWriteMode() const
{
	return writeMode;
}

void UserService::SetWriteMode(const std::string& newMode)
{
	if (newMode == "CSV")
	{
		writeMode = newMode;
		writer = new CSVMovieListWriter("movie_list.csv");
	}
	else if (newMode == "HTML")
	{
		writeMode = newMode;
		writer = new HTMLMovieListWriter("movie_list.html");
	}
	else
	{
		throw std::exception("Unhandled mode!");
	}
}