#include "ui.h"

using namespace std;

int main()
{
	Repository repo{};

	Service serv{ repo };
	Console ui{ serv };
	ui.run();

	return 0;
}