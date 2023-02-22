#pragma once
#include "domain.h"
#include "dynamicVector.h"

class Repository
{
private:
	DynamicVector<Player> players;

public:
	// default constructor
	Repository(){}

	/*
		Adds a player to the repository.
		Input: p - player.
		Output: the player is added to the repository.
	*/
	void addPlayer(const Player& p);

	/*
		Removes a player from the repository.
		Input: pos - the position of the player in the repository.
		Output: the player is removes from the repository.
	*/
	void removePlayer(int pos);
	/*
		Searches a player by name.
		Input: name - name of the player.
		Output: The position of the player if found, False otherwise.
	*/
	int findByName(std::string name) const;

	DynamicVector<Player> getPlayers() const;
};