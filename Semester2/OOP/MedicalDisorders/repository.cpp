#include "repository.h"

Repository::Repository(const std::string& fileName) : fileName{fileName}
{
	readData();
}

Repository::~Repository()
{
}

std::vector<Disorder> Repository::getDisorders()
{
	return this->disorders;
}

void Repository::readData()
{
	std::ifstream inputFile;
	inputFile.open(fileName, std::ios::in);

	Disorder next;
	while (inputFile >> next)
		disorders.push_back(next);

	inputFile.close();
}
