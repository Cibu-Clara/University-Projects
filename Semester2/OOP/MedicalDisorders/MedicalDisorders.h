#pragma once
#include <QtWidgets/QMainWindow>
#include "ui_MedicalDisorders.h"
#include "service.h"

class MedicalDisorders : public QMainWindow
{
    Q_OBJECT

public:
    MedicalDisorders(Service* serv, QWidget *parent = Q_NULLPTR);
    ~MedicalDisorders();

private:
    Ui::MedicalDisordersClass ui;
    Service* serv;
    void populateList();
    void updateList(std::string symptoms);

public slots:
    void updateListHandler();
};
