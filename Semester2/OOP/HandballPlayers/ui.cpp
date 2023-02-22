#include "ui.h"
#include "exception.h"
#include<string>

using namespace std;

Console::Console( Service& s) :
	serv{ s }
{
	serv.initializeRepo();
}

void Console::printMenu()
{
	cout << endl;
	cout << "1. Add new player" << endl;
	cout << "2. Remove existing player" << endl;
	cout << "3. List all players" << endl;
	cout << "0. Exit" << endl;
}

void Console::addUI()
{
	cout << "Enter the name: ";
	std::string name;
	getline(cin, name);
	cout << "Enter the nationality: ";
	std::string nationality;
	getline(cin, nationality);
	cout << "Enter the team: ";
	std::string team;
	getline(cin, team);
	cout << "Enter the score:";
	int score;
	cin >> score;
	cin.ignore();

	this->serv.addServ(name, nationality, team, score);
}

void Console::removeUI()
{
	std::string name;
	cout << "Enter the name of the player you want to remove: ";
	cin >> name;

	this->serv.removeServ(name);

}

void Console::displayAllPlayers()
{	
	cout << endl;
	DynamicVector<Player> players = this->serv.getRepo().getPlayers();
	if (players.getSize() == 0)
	{
		cout << "There are no players in the repository." << endl;
		return;
	}

	for (int i = 0; i < players.getSize(); i++)
	{
		Player p = players[i];
		cout << p.getName() << " | " << p.getNationality() << " | " << p.getTeam() << " | " << p.getScore() << endl;
	}
}


void Console::run()
{	
	while (true)
	{
		Console::printMenu();
		int command{ 0 };
		cout << "Enter command: ";
		cin >> command;
		cin.ignore();
		if (command == 0)
			break;
		else
			try
			{
				switch (command)
				{
				case 1:
					this->addUI();
					cout << "Player succesfully added!\n";
					break;
				case 2:
					this->removeUI();
					cout << "Player succesfully removed!\n";
					break;
				case 3:
					this->displayAllPlayers();
					break;
				}
			}
			catch (const RepoException& re) 
			{
				cout << re.getMessage();
			}
			catch (const ValidatorException& ve) 
			{
				cout << ve.getMessage();
			}
	}
}