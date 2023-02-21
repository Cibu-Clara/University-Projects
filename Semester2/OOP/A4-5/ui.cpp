#define _CRT_SECURE_NO_WARNINGS
#include "ui.h"
#include <iostream>
#include <string.h>
#include <windows.h>

Console::Console(AdminService& adminService, UserService& userService) :
	adminService{ adminService }, userService{ userService }
{
	adminService.InitializeRepo();
}

void Console::SelectMode()
{
	bool done = false;

	while (1)
	{
		std::cout << "Open application in:\n";
		std::cout << "1.Administrator mode\n";
		std::cout << "2.User mode\n";
		std::cout << "0.Exit\n";
		std::string mode;
		std::cin >> mode;

		if (mode[0] != '0' && mode[0] != '1' && mode[0] != '2')
		{
			std::cout << "Invalid mode!\n";
		}
		if (mode[0] == '0')
			break;
		switch (mode[0])
		{
		case '1':
		{
			MainLoopAdmin();
			break;
		}
		case '2':
		{
			MainLoopUser();
			break;
		}
		}
	}
}

void Console::PrintAdminMenu()
{
	std::cout << "1.Add a new movie\n";
	std::cout << "2.Remove a movie by name\n";
	std::cout << "3.Update a movie\n";
	std::cout << "4.Print all available movies\n";
	std::cout << "0.Exit\n";
}

void Console::MainLoopUser()
{
	std::string command;
	bool done = false;

	while (!done)
	{
		PrintUserMenu();
		std::cout << "\nPlease input your command:\n";
		std::cin >> command;
		if (command.size() != 1)
			std::cout << "Invalid command!\n";
		else {
			try {
				switch (command[0])
				{
				case '1':
				{
					AddWatchListNoFilter();
					break;
				}
				case '2':
				{
					AddWatchListFiter();
					break;
				}
				case '3':
				{
					DeleteMovieFromWatchList();
					break;
				}
				case '4':
				{
					ViewWatchList();
					break;
				}
				case '0':
				{
					done = true;
					break;
				}
				default:
				{
					break;
				}
				}

			}
			catch (std::exception& e)
			{
				std::cout << e.what() << "\n";
			}
		}
	}

}

void Console::PrintUserMenu()
{
	std::cout << "1.View all available movies\n";
	std::cout << "2.Filter movies by genre\n";
	std::cout << "3.Delete movie from watch list\n";
	std::cout << "4.View watch list\n";
	std::cout << "0.Exit\n";
}

void Console::AddWatchListNoFilter()
{
	userService.ReinitializeMovieList();
	std::string command;
	bool done = false;

	while (!done)
	{
		std::cout << userService.GetCurrentMovie();
		std::string trailer = userService.GetCurrentMovie().GetTrailer();
		char* arr = new char[100];
		strcpy(arr, "start ");
		strcat(arr, trailer.c_str());
		system(arr);
		std::cout << "Would you like to add this movie to the watch list? (Y-yes, N-no, E-exit)\n";
		std::cin >> command;

		if (command.size() != 1) std::cout << "Invalid command!\n";
		else {
			try {
				switch (command[0])
				{
				case 'y':
				case 'Y':
				{
					AddWatchList();
					break;
				}
				case 'n':
				case 'N':
				{
					NoAddWatchList();
					break;
				}
				case 'e':
				case 'E':
				{
					done = true;
					break;
				}
				default:
					break;
				}
			}
			catch (std::exception& e)
			{
				std::cout << e.what();
			}
		}

	}
}

void Console::AddWatchListFiter()
{
	UserReinitializationWatchList();
	UserFilterByGenre();
	std::string command;
	bool done = false;

	while (!done)
	{
		std::cout << userService.GetCurrentMovie();
		std::cout << "Would you like to add this movie to the watch list? (Y-yes, N-no, E-exit)\n";
		std::cin >> command;

		if (command.size() != 1) std::cout << "Invalid command!\n";
		else {
			try {
				switch (command[0])
				{
				case 'y':
				case 'Y':
				{
					AddWatchList();
					break;
				}
				case 'n':
				case 'N':
				{
					NoAddWatchList();
					break;
				}
				case 'e':
				case 'E':
				{
					done = true;
					break;
				}
				default:
					break;
				}
			}
			catch (std::exception& e)
			{
				std::cout << e.what();
			}
		}

	}

}

void Console::DeleteMovieFromWatchList()
{
	std::cout << "Enter the movie you like to remove:\n";
	std::string movie;
	std::cin >> movie;
	std::cout << "Rate the movie. Enter the number of likes (positive number).\n";
	size_t likes;
	std::cin >> likes;
	userService.DeleteMovieWatchList(movie, likes);
}

void Console::ViewWatchList()
{
	std::cout << userService.GetMovieList();
}

void Console::AddWatchList()
{
	userService.AddToWatchList(userService.GetCurrentMovie());
}

void Console::NoAddWatchList()
{
	userService.GoToNextMovie();
}

void Console::UserReinitializationWatchList()
{
	userService.ReinitializeMovieList();
}

void Console::UserFilterByGenre()
{
	std::string genre;
	std::cout << "Enter the genre you like to filter by:\n";
	std::cin >> genre;
	userService.FilterByGenre(genre);
}

void Console::MainLoopAdmin()
{
	std::string command;
	bool done = false;

	while (!done)
	{
		PrintAdminMenu();
		std::cout << "\nPlease input your command:\n";
		std::cin >> command;
		if (command.size() != 1)
			std::cout << "Invalid command!\n";
		else {
			try {
				switch (command[0])
				{
				case '1':
				{
					AddMovie();
					break;
				}
				case '2':
				{
					RemoveMovie();
					break;
				}
				case '3':
				{
					UpdateMovie();
					break;
				}
				case '4':
				{
					PrintAllMovies();
					break;
				}
				case '0':
				{
					done = true;
					break;
				}
				default:
				{
					std::cout << "Invalid command!\n";
					break;
				}
				}

			}
			catch (std::exception& e)
			{
				std::cout << e.what() << "\n";
			}
		}
	}
}

void Console::AddMovie()
{
	std::string title, genre, trailer;
	size_t year, likes;
	std::cout << "Enter title, genre, year of release, nr of likes and trailer:\n";
	std::cin >> title;
	std::cin >> genre;
	std::cin >> year;
	std::cin >> likes;
	std::cin >> trailer;

	this->adminService.AddMovie(title, genre, year, likes, trailer);

}

void Console::RemoveMovie()
{
	std::string title;
	std::cout << "Enter the title of the movie you want to remove:\n";
	std::cin >> title;

	this->adminService.RemoveMovie(title);
}

void Console::UpdateMovie()
{
	std::string title;
	std::cout << "Enter the title of the movie you want to update:\n";
	std::cin >> title;
	std::cout << "Enter the attribute you would like to modify: "
		<< "T-title, G-genre, Y-year, L-likes, K-trailer link\n";

	std::string updateAttribute;
	std::cin >> updateAttribute;

	if (updateAttribute.size() != 1)
	{
		std::cout << "Invalid command!\n";
		return;
	}
	switch (updateAttribute[0])
	{
	case 't':
	case 'T':
	{
		std::string newTitle;
		std::cout << "Enter the new title of the movie:\n";
		std::cin >> newTitle;
		UpdateMovieTitle(title, newTitle);
		break;
	}
	case 'g':
	case 'G':
	{
		std::string newGenre;
		std::cout << "Enter the new genre of the movie:\n";
		std::cin >> newGenre;
		UpdateMovieGenre(title, newGenre);
		break;
	}
	case 'y':
	case 'Y':
	{
		size_t newYear;
		std::cout << "Enter the new realeasing year of the movie:\n";
		std::cin >> newYear;
		UpdateMovieYear(title, newYear);
		break;
	}
	case 'l':
	case 'L':
	{
		size_t newLikes;
		std::cout << "Enter the new number of likes of the movie:\n";
		std::cin >> newLikes;
		UpdateMovieLikes(title, newLikes);
		break;
	}
	case 'k':
	case 'K':
	{
		std::string newTrailer;
		std::cout << "Enter the new trailer of the movie:\n";
		std::cin >> newTrailer;
		UpdateMovieTrailer(title, newTrailer);
		break;
	}
	default:
	{
		std::cout << "Invalid attribute!\n";
		break;
	}
	}
}

void Console::UpdateMovieTitle(std::string title, std::string newTitle)
{
	this->adminService.UpdateMovieTitle(title, newTitle);
}

void Console::UpdateMovieGenre(std::string title, std::string newGenre)
{
	this->adminService.UpdateMovieGenre(title, newGenre);
}

void Console::UpdateMovieYear(std::string title, size_t newYear)
{
	this->adminService.UpdateMovieYear(title, newYear);
}

void Console::UpdateMovieLikes(std::string title, size_t newLikes)
{
	this->adminService.UpdateMovieLikes(title, newLikes);
}

void Console::UpdateMovieTrailer(std::string title, std::string newTrailer)
{
	this->adminService.UpdateMovieTrailer(title, newTrailer);
}

void Console::PrintAllMovies()
{
	std::cout << adminService.GetRepo();
}
