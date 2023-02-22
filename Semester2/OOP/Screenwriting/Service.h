#pragma once

#include "Repository.h"

class Service
{
public:
	Service() = default;
	Service(Repository<Writer>* writer, Repository<Idea>* ideas);
	~Service() = default;

	void addIdea(std::string description, int act, Writer w);

private:
	Repository<Writer>* writers;
	Repository<Idea>* ideas;
};

