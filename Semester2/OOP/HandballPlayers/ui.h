#pragma once
#include "service.h"

class Console
{
private: 
	Service serv;

public:
	Console(Service& s);

	void run();

private:
	static void printMenu();

	void addUI();
	void removeUI();
	void displayAllPlayers();

};