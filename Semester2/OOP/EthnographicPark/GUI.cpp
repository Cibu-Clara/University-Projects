#include "GUI.h"

void GUI::openEthWindows() {
    vector<Ethnologist>& ethnologists = this->service.get_ethnologists();
    for (auto& ethnologist : ethnologists) {
        auto* newEthWindow = new EthWindow(this->service, ethnologist);
        this->ethWindows.push_back(newEthWindow);
        newEthWindow->setMinimumSize(800, 400);
        newEthWindow->show();
    }
}

GUI::~GUI() {

}

GUI::GUI(Service& service1) : service(service1) {
    openEthWindows();
    openParkMap();
}

void GUI::openParkMap() {
    MapWindow* pWindow = new MapWindow(this->service);
    pWindow->show();
}