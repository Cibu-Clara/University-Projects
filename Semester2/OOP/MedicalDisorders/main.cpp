#include "MedicalDisorders.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repository* repo = new Repository("file.txt");
    Service* serv = new Service(*repo);

    MedicalDisorders w{serv};
    w.show();
    return a.exec();
}
