#pragma once

#include <QWidget>
#include "ui_EthWindow.h"
#include "Observer.h"
#include "Service.h"
#include "TableModel.h"
#include "QTableView"
#include <QSortFilterProxyModel>
#include "QVBoxLayout"
#include "QPushButton"
#include "QLineEdit"
#include "QFormLayout"
#include "QMessageBox"

class EthWindow : public Observer, public QWidget {
private:
    Ethnologist& ethnologist;
    Service& service;
    void initEthWindow();
    void connectSignalsAndSlots();

    /// graphical
    TableModel* tableModel;
    QTableView* tableView;
    QSortFilterProxyModel* filterModel;
    QVBoxLayout* mainLayout;
    QPushButton* addButton, * updateButton;
    QLineEdit* descriptionLineEdit;
    QLineEdit* locationLineEdit;
    QFormLayout* addBuildingLayout;

public:
    void update() override;
    EthWindow(Service& service1, Ethnologist& ethnologist1);

    void colorYoursBlue();

    void addBuilding();

    int getSelectedIndex();

    void updateBuilding();

    void checkYourArea();

    ~EthWindow();
};
