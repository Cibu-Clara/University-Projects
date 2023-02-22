#include "EthWindow.h"

void EthWindow::initEthWindow() {
    this->setWindowTitle(QString::fromStdString(this->ethnologist.getName()));
    this->setPalette(QPalette(QColor(rand() % 255, rand() % 255, rand() % 255)));

    this->tableModel = new TableModel{ this->service, this->ethnologist };
    this->tableView = new QTableView{};

    //    this->filterModel = new QSortFilterProxyModel{};
    //    this->filterModel->setSourceModel(this->tableModel);
    //
    //    this->tableView->setSortingEnabled(false);
    //    this->filterModel->sort(2,Qt::AscendingOrder);
    this->tableView->setModel(this->tableModel);


    this->mainLayout = new QVBoxLayout();
    this->mainLayout->addWidget(this->tableView);

    this->addBuildingLayout = new QFormLayout();

    this->descriptionLineEdit = new QLineEdit();
    addBuildingLayout->addRow("Description: ", this->descriptionLineEdit);

    this->locationLineEdit = new QLineEdit();
    addBuildingLayout->addRow("Location, separated by space", this->locationLineEdit);

    this->addButton = new QPushButton("Add building");
    addBuildingLayout->addWidget(this->addButton);
    this->updateButton = new QPushButton("Update building");
    addBuildingLayout->addWidget(this->updateButton);

    mainLayout->addLayout(addBuildingLayout);


    this->setLayout(mainLayout);
}

void EthWindow::connectSignalsAndSlots() {
    QObject::connect(addButton, &QPushButton::clicked, this, &EthWindow::addBuilding);
    QObject::connect(updateButton, &QPushButton::clicked, this, &EthWindow::updateBuilding);
    QObject::connect(this->tableView, &QTableView::clicked, this, &EthWindow::checkYourArea);
}

void EthWindow::update() {
    this->tableModel->updateInternalData();
}

EthWindow::EthWindow(Service& service1, Ethnologist& ethnologist1) : service{ service1 }, ethnologist{ ethnologist1 } {
    this->service.addObserver(this);
    initEthWindow();
    connectSignalsAndSlots();
}

EthWindow::~EthWindow() {
    this->service.removeObserver(this);
}


void EthWindow::addBuilding() {
    string description = this->descriptionLineEdit->text().toStdString();
    string location = this->locationLineEdit->text().toStdString();

    if (description.empty() || location.empty()) {
        QMessageBox::critical(this, "Error", "Invalid description or location!");
    }
    else {
        try {
            this->service.addBuilding(description, this->ethnologist.getArea(), location);
            this->descriptionLineEdit->clear();
            this->locationLineEdit->clear();
        }
        catch (exception& e) {
            QMessageBox::critical(this, "Error", e.what());
        }
    }
}

void EthWindow::updateBuilding() {
    int index = this->getSelectedIndex();
    if (index == -1) {
        QMessageBox::critical(this, "Error", "Invalid selection!");
        return;
    }
    int id = this->tableModel->index(index, 0).data().toInt();
    string description = this->descriptionLineEdit->text().toStdString();
    string location = this->locationLineEdit->text().toStdString();

    if (description.empty() || location.empty()) {
        QMessageBox::critical(this, "Error", "Invalid description or location!");
    }
    else {
        try {
            this->service.updateBuilding(id, description, location);
        }
        catch (exception& e) {
            QMessageBox::critical(this, "Error", e.what());
        }
    }

}

void EthWindow::checkYourArea() {
    int index = this->getSelectedIndex();

    string area = this->tableModel->index(index, 2).data().toString().toStdString();
    if (area == this->ethnologist.getArea()) {
        this->updateButton->setEnabled(true);
        return;
    }
    else {
        this->updateButton->setEnabled(false);
        return;
    }
}

int EthWindow::getSelectedIndex() {
    QModelIndexList selectedIndexes = this->tableView->selectionModel()->selectedIndexes();
    if (selectedIndexes.isEmpty())
        return -1;
    int selectedIndex = selectedIndexes.at(0).row();
    return selectedIndex;
}
