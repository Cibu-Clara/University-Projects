#pragma once
#include "disorder.h"
#include <fstream>

class Repository
{
public:
	Repository(const std::string& fileName);
	~Repository();
	std::vector<Disorder> getDisorders();

protected:
	std::string fileName;

private:
	std::vector<Disorder> disorders;
	void readData();
};

