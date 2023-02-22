#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Test.h"
#include "Service.h"
class Test : public QMainWindow
{
    Q_OBJECT

public:
    Test(Service* serv, QWidget *parent = Q_NULLPTR);
    ~Test();
    void populateList();
    void populateFilterList(int priority);
    void filterByPrecipitation(int pp);
    void total(int start, std::string descr);

private:
    Ui::TestClass ui;
    Service* serv;

public slots:
    void filterHandler();
    void totalHandler();

};
