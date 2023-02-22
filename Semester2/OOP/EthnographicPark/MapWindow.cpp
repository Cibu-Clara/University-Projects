#include "MapWindow.h"

MapWindow::MapWindow(Service& service1) : service{ service1 } {
    this->service.addObserver(this);
    this->setWindowTitle(QString::fromStdString("Park Map"));
}

MapWindow::~MapWindow() {
    this->service.removeObserver(this);
}

void MapWindow::update() {
    this->repaint();
}

QSize MapWindow::sizeHint() const {
    return QWidget::sizeHint();
}

void MapWindow::drawPark() {
    QPainter painter{ this };
    painter.drawRect(x, y, 500, 500);
    painter.drawText(x + 50, y + 50, "Park is not ready yet. :)");
    vector<Building> buildings = this->service.get_buildings();
        for(auto building : buildings){
            for(auto square : building.getLocation())
            painter.drawRect(x + square[0], y + square[1], 10, 10);
        }

}

void MapWindow::resizeEvent(QResizeEvent* event) {
    this->x = 0;
    this->y = 0;
}

void MapWindow::paintEvent(QPaintEvent* event) {
    this->drawPark();
}