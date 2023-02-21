#include "ui.h"
#include <iostream>
#include <memory>
#include "tests.h"

int main()
{	
	Tests::TestAll();
	
	Repository* repo = new Repository(8);
	AdminService* adminService = new AdminService(*repo);
	UserService* userService = new UserService(*repo);
	Console* console = new Console(*adminService, *userService);

	console->SelectMode();

	delete console;
	delete adminService;
	delete userService;
	delete repo;
	
	return 0;
}