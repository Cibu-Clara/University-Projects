#pragma once
#include "Repository.h"

class Service
{
public:
	Service(Repository& repo);
	~Service();
	std::vector<Weather> getAll();
	std::vector<Weather> getFiltered(int pp);
private:
	Repository& repo;
};

