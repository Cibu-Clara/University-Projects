#include "Test.h"
#include <QMessageBox>

Test::Test(Service* serv, QWidget *parent)
    : QMainWindow(parent), serv(serv)
{
    ui.setupUi(this);
    this->populateList();
}

Test::~Test()
{
}

void Test::populateList()
{
    this->ui.weatherListWidget->clear();
    std::vector<Weather> weather = this->serv->getAll();
    sort(weather.begin(), weather.end(), [](const Weather& a, const Weather& b) {return (a.getStart() < b.getStart()); });

    for (Weather& w : weather)
        this->ui.weatherListWidget->addItem(QString::fromStdString(w.toString()));
}

void Test::filterHandler()
{
    int pp = this->ui.filterLineEdit->text().toInt();
    this->ui.filterLineEdit->clear();
    filterByPrecipitation(pp);
}

void Test::filterByPrecipitation(int pp)
{
    populateFilterList(pp);
    
}

void Test::total(int start, std::string descr)
{
    std::vector<Weather> weather = this->serv->getAll();
    int total = 0;
    bool found = 0;
    for (Weather& w : weather)
        if (start <= w.getStart() and descr == w.getDescription())
            total += w.getEnd() - w.getStart(), found = 1;
    if (found == 0)
    {
        QMessageBox::warning(this, "Warning", "No weather found!");
        return;
    }
    else
        this->ui.totalLineEdit->setText(QString::fromStdString(std::to_string(total)));
}

void Test::totalHandler()
{
    int start = this->ui.timeLineEdit->text().toInt();
    std::string descr = this->ui.descriptionLineEdit->text().toStdString();
    this->ui.timeLineEdit->clear();
    this->ui.descriptionLineEdit->clear();
    total(start, descr);
}


void Test::populateFilterList(int pp)
{
    this->ui.weatherListWidget->clear();
    std::vector<Weather> weather = this->serv->getFiltered(pp);
    sort(weather.begin(), weather.end(), [](const Weather& a, const Weather& b) {return (a.getStart() < b.getStart()); });

    for (Weather& w : weather)
        this->ui.weatherListWidget->addItem(QString::fromStdString(w.toString()));
}