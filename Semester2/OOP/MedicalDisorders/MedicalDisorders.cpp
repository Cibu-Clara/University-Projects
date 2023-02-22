#include "MedicalDisorders.h"

MedicalDisorders::MedicalDisorders(Service* serv, QWidget *parent)
    : QMainWindow(parent), serv(serv)
{
    ui.setupUi(this);
    this->populateList();
}

MedicalDisorders::~MedicalDisorders()
{
}

void MedicalDisorders::populateList()
{
    this->ui.disordersListWidget->clear();
    std::vector<Disorder> disorders = this->serv->getAll();
    sort(disorders.begin(), disorders.end(), [](const Disorder& a, const Disorder& b) { return (a.getName() < b.getName()); });
    for (Disorder& d : disorders)
        this->ui.disordersListWidget->addItem(QString::fromStdString(d.toString()));
}

void MedicalDisorders::updateListHandler()
{
    std::string symptoms = this->ui.searchBar->text().toStdString();
    updateList(symptoms);

}

void MedicalDisorders::updateList(std::string symptoms)
{
    this->ui.disordersListWidget->clear();
    std::vector <Disorder> newList = this->serv->query(symptoms);
    for (auto& i : newList) {
        QString res = QString::fromStdString(i.getCategory() + " | " + i.getName());
        QListWidgetItem* x = new QListWidgetItem(res);
        this->ui.disordersListWidget->addItem(x);
    }
}