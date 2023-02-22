#pragma once

#include "ui_GUI.h"
#include <QtWidgets/QWidget>
#include "Service.h"
#include "EthWindow.h"
#include "MapWindow.h"

class GUI : public QWidget {
private:
    Service service;

    void openEthWindows();
    void openParkMap();

    /// graphical elements
    vector<QWidget*> ethWindows;

public:
    ~GUI();
    explicit GUI(Service& service1);

};