#include "TaskManager.h"
#include "repository.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repository* repo = new Repository();
    Service* serv = new Service(*repo);

    TaskManager gui{serv};
    gui.show();

    return a.exec();
}
