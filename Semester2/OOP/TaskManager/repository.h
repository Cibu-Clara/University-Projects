#pragma once
#include "domain.h"
#include <vector>
#include <fstream>

class Repository
{
public:
	Repository();
	~Repository();
	std::vector<Task> getTasks() const;

private:
	std::vector<Task> tasks;
	void readData();
};

