#include "userService.h"
#include <assert.h>

UserService::UserService(Repository& repo) :
	repo{ repo }, CurrentList{ repo.GetArray() }, WatchList{ DynamicVector < Movie >(1) }, index{ 0 }
{
}

void UserService::AddToWatchList(const Movie& movie)
{
	bool found = false;

	for (size_t i = 0; i < WatchList.GetSize() && !found; ++i)
		if (movie == WatchList[i]) found = true;

	if (found) throw std::exception("The movie has already been added to the watch list!");
	WatchList.AddElement(movie);
	GoToNextMovie();

}

void UserService::GoToNextMovie()
{
	if (index == CurrentList.GetSize() - 1)
	{
		index = 0;
		return;
	}
	index++;
}

void UserService::FilterByGenre(std::string genre)
{
	size_t i = 0;

	if (genre != "")
		while (i != CurrentList.GetSize() - 1)
		{
			if (CurrentList[i].GetGenre() != genre)
				CurrentList.RemoveElement(i), i--;
			i++;
		}
	index = 0;
}

void UserService::DeleteMovieWatchList(std::string movie, size_t likes)
{
	bool found = false;
	for (size_t i = 0; i < WatchList.GetSize() && !found; i++)
		if (WatchList[i].GetTitle() == movie)
		{
			found = true;
			if (likes > 0)
			{
				size_t currentLikes = WatchList[i].GetLikes() + likes;
				bool found1 = false;
				for (size_t j = 0; j < CurrentList.GetSize() && !found1; j++)
					if (CurrentList[j].GetTitle() == movie)
					{
						found1 = true;
						repo.UpdateLikes(j, currentLikes);
						CurrentList[j].SetLikes(currentLikes);
					}
			}

			WatchList.RemoveElement(i);
		}
	if (!found) throw std::exception("The movie you want to remove does not exist!");
}

void UserService::ReinitializeMovieList()
{
	if (CurrentList == repo.GetArray())
		return;

	CurrentList = repo.GetArray();
	index = 0;
}

DynamicVector<Movie> UserService::GetMovieList() const
{
	return WatchList;
}

Movie& UserService::GetCurrentMovie() const
{
	return CurrentList[index];
}
