#pragma once

#include "ui_MapWindow.h"
#include "Observer.h"
#include <QWidget>
#include <QPainter>
#include "Service.h"

class MapWindow : public Observer, public QWidget {
private:
    Service& service;
    int x;
    int y;
public:
    MapWindow(Service& service1);
    ~MapWindow();
    void update() override;

    QSize sizeHint() const Q_DECL_OVERRIDE;

    void drawPark();

    void resizeEvent(QResizeEvent* event) override;

    void paintEvent(QPaintEvent* event) override;

};

