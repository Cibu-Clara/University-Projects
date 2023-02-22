#pragma once
#include "repository.h"

class Service
{
private:
	Repository repo;
public:
	Service( Repository& r);

	Repository getRepo() const;

	// adds a player with the given data to the player repository
	void addServ(const std::string& name, const std::string& nationality, const std::string& team, const int score);

	// removes a player with the given name from the repository
	void removeServ(std::string name);

	// adds 5 entries to the repo
	void initializeRepo();
};