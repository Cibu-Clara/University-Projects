#include "GUI.h"
#include <QtWidgets/QApplication>
#include <iostream>

int main(int argc, char** argv) {
    QApplication a{ argc, argv };

    Repository repository;
    Service service{ repository };
    GUI gui{ service };

    return a.exec();
}