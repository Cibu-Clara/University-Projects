#include "fileRepository.h"
#include <fstream>

FileRepository::FileRepository(const std::string& path) :
	Repository{ Repository() },
	filePath{ path }
{
	ReadData();
}

void FileRepository::ReadData()
{
	std::ifstream inputFile;
	inputFile.open(filePath, std::ios::in);

	Movie next;
	while (inputFile >> next)
		Repository::AddElement(next);

	inputFile.close();
}

void FileRepository::WriteData()
{
	std::ofstream outputFile;
	outputFile.open(filePath, std::ios::out);

	for (const Movie& d : Repository::GetMovies())
		outputFile << d;

	outputFile.close();
}

void FileRepository::AddElement(const Movie& newMovie)
{
	Repository::AddElement(newMovie);
	WriteData();
}

void FileRepository::RemoveElemnt(size_t position)
{
	Repository::RemoveElemnt(position);
	WriteData();
}

void FileRepository::UpdateTitle(size_t position, std::string newTitle)
{
	Repository::UpdateTitle(position, newTitle);
	WriteData();
}

void FileRepository::UpdateGenre(size_t position, std::string newTitle)
{
	Repository::UpdateTitle(position, newTitle);
	WriteData();
}

void FileRepository::UpdateYear(size_t position, size_t newYear)
{
	Repository::UpdateYear(position, newYear);
	WriteData();
}

void FileRepository::UpdateLikes(size_t position, size_t newLikes)
{
	Repository::UpdateLikes(position, newLikes);
	WriteData();
}


void FileRepository::UpdateTrailer(size_t position, std::string newTrailer)
{
	Repository::UpdateTrailer(position, newTrailer);
	WriteData();
}


