#pragma once
#include <QtWidgets/QMainWindow>
#include "ui_TaskManager.h"
#include "service.h"

class TaskManager : public QMainWindow
{
    Q_OBJECT

public:
    TaskManager(Service* serv, QWidget *parent = Q_NULLPTR);
    ~TaskManager();

private:
    Ui::TaskManagerClass ui;
    Service* serv;
    void populateList();
    void populateFilterList(int priority);
    void filterByPriority(int priority);
    void totalDuration(int priority);

public slots:
    void filterHandler();
    void totalDurationHandler();
};
