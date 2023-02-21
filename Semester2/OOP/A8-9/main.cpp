#include "ui.h"
#include <iostream>

int main()
{
	FileRepository* repo = new FileRepository("file.txt");
	AdminService* adminService = new AdminService(*repo);
	UserService* userService = new UserService(*repo);
	Console* console = new Console(*adminService, *userService);

	console->Start();

	delete console;
	delete adminService;
	delete userService;
	delete repo;

	return 0;
}