#include "Test.h"
#include <QtWidgets/QApplication>
#include "Service.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repository* repo = new Repository();
    Service* serv = new Service(*repo);
    Test w{serv};
    w.show();
    return a.exec();
}
