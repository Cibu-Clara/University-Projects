#include "MovieList.h"
#include "HTMLTable.h"
#include <fstream>

MovieList::MovieList() :
	movieList{ std::vector<Movie>() }
{
}

void MovieList::AddMovie(const Movie& movie)
{
	auto pos = std::find(movieList.begin(), movieList.end(), movie);

	if (pos != movieList.end()) throw std::exception("Movie already in movie list!");
	movieList.push_back(movie);
}

const std::vector<Movie>& MovieList::GetList() const
{
	return movieList;
}

CSVMovieListWriter::CSVMovieListWriter(const std::string& fileName)
{
	SetFileName(fileName);
}

void CSVMovieListWriter::WriteToFile(const MovieList& movieList)
{
	std::ofstream outputFile;
	outputFile.open(fileName, std::ios_base::out);

	for (auto& m : movieList.GetList())
	{
		outputFile << m.GetTitle() << ',' << m.GetGenre() << ',' << m.GetYear() << ',' << m.GetLikes() << ',' << m.GetTrailer();
		outputFile << '\n';
	}

	outputFile.close();
}

HTMLMovieListWriter::HTMLMovieListWriter(const std::string& fileName)
{
	SetFileName(fileName);
}

void HTMLMovieListWriter::WriteToFile(const MovieList& movieList)
{
	std::ofstream outputFile;
	outputFile.open(fileName, std::ios_base::out);

	outputFile << "<!DOCTYPE html>";

	outputFile << "<html>";
	outputFile << "<head>";
	outputFile << "<title> Movie List </title>";
	outputFile << "</head>";

	outputFile << "<body>";

	HTMLTable table = HTMLTable();
	HTMLTable::Row topRow;
	topRow.AddToRow("Title");
	topRow.AddToRow("Genre");
	topRow.AddToRow("Year");
	topRow.AddToRow("Number of likes");
	topRow.AddToRow("Trailer");
	table.AddRow(topRow);
	for (auto& m : movieList.GetList())
	{
		HTMLTable::Row newRow;
		newRow.AddToRow(m.GetTitle());
		newRow.AddToRow(m.GetGenre());
		newRow.AddToRow(std::to_string(m.GetYear()));
		newRow.AddToRow(std::to_string(m.GetLikes()));
		newRow.AddToRow(m.GetTrailer());
		table.AddRow(newRow);
	}

	outputFile << table;

	outputFile << "</body>";

	outputFile << "</html>";

	outputFile.close();
}

const std::string& MovieListWriter::GetFileName() const
{
	return fileName;
}

void MovieListWriter::SetFileName(const std::string& newFile)
{
	fileName = newFile;
}
