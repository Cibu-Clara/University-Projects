#include <QtWidgets/QApplication>
#include "fileRepository.h"
#include "GUI.h"

int main(int argc, char* argv[])
{
    QApplication a(argc, argv);
    FileRepository* repo = new FileRepository("file.txt");
    AdminService* adminService = new AdminService(*repo);
    UserService* userService = new UserService(*repo);
    //Console* console = new Console(*adminService, *userService);

    GUI gui{ adminService, userService };
    gui.show();

    return a.exec();
}
