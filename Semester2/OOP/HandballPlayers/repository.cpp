#include "repository.h"
#include "exception.h"

void Repository::addPlayer(const Player& p)
{
	bool duplicate = false;
	DynamicVector<Player> players = getPlayers();
	for (int i = 0; i < players.getSize(); i++)
		if (players[i] == p) 
			duplicate = true;

	if (duplicate) 
		throw RepoException("A player with the same name already exists!\n");

	this->players.addElem(p);
}

void Repository::removePlayer(int pos)
{
	if (pos == -1)
		throw RepoException("Player not found!\n");
	this->players.removeElem(pos);
}

int Repository::findByName(std::string name) const
{
	int pos = -1;
	for (int i = 0; i < players.getSize(); i++)
		if ((this->players)[i].getName() == name)
		{
			pos = i;
			break;
		}
	return pos;
}

DynamicVector<Player> Repository::getPlayers() const
{
	return this->players;
}