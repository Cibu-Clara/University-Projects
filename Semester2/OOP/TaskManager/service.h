#pragma once
#include "repository.h"

class Service
{
public:
	Service(Repository& repo);
	~Service();
	std::vector<Task> getAll();
	std::vector<Task> getFiltered(int priority);
private:
	Repository& repo;
};

