#pragma once
#include "repository.h"

class Service
{
public:
	Service(Repository& repo);
	~Service();
	std::vector<Disorder> getAll();
	std::vector<Disorder> query(std::string q);

private:
	Repository& repo;
};

