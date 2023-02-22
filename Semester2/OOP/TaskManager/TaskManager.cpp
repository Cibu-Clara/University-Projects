#include "TaskManager.h"
#include <vector>
#include <QMessageBox>

TaskManager::TaskManager(Service* serv, QWidget *parent)
    : QMainWindow(parent), serv(serv)
{
    ui.setupUi(this);
    this->populateList();
}

TaskManager::~TaskManager()
{
}

void TaskManager::populateList()
{
    this->ui.tasksListWidget->clear();
    std::vector<Task> tasks = this->serv->getAll();
    sort(tasks.begin(), tasks.end(), [](const Task& a, const Task& b) { return (a.getPriority() < b.getPriority()); });
    sort(tasks.begin(), tasks.end(), [](const Task& a, const Task& b) { return (a.getPriority() == b.getPriority() && (a.getDuration() > b.getDuration())); });
    
    for (Task& t : tasks)
        if (t.getPriority() == 1)
        {
            QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(t.getDescription() + " | " +
                std::to_string(t.getDuration()) + " | " + std::to_string(t.getPriority())));
            QFont font;
            font.setBold(true);
            item->setFont(font);
            this->ui.tasksListWidget->addItem(item);
        }
        else if (t.getPriority() == 2)
        {
            QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(t.to_string()));
            item->setBackground(Qt::red);
            this->ui.tasksListWidget->addItem(item);
        }
        else
            this->ui.tasksListWidget->addItem(QString::fromStdString(t.to_string()));
}

void TaskManager::populateFilterList(int priority)
{
    this->ui.tasksListWidget->clear();
    std::vector<Task> tasks = this->serv->getFiltered(priority);
    sort(tasks.begin(), tasks.end(), [](const Task& a, const Task& b) { return (a.getPriority() < b.getPriority()); });
    sort(tasks.begin(), tasks.end(), [](const Task& a, const Task& b) { return (a.getPriority() == b.getPriority() && (a.getDuration() > b.getDuration())); });

    for (Task& t : tasks)
        if (t.getPriority() == 1)
        {
            QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(t.to_string()));
            QFont font;
            font.setBold(true);
            item->setFont(font);
            this->ui.tasksListWidget->addItem(item);
        }
        else if (t.getPriority() == 2)
        {
            QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(t.to_string()));
            item->setBackground(Qt::red);
            this->ui.tasksListWidget->addItem(item);
        }
        else
            this->ui.tasksListWidget->addItem(QString::fromStdString(t.to_string()));
}

void TaskManager::filterByPriority(int priority)
{
    if (priority == 1 || priority == 2 || priority == 3)
    {
        populateFilterList(priority);
    }
    else {
        populateList();
    }
}

void TaskManager::filterHandler()
{
    int priority = this->ui.filterLineEdit->text().toInt();
    this->ui.filterLineEdit->clear();
    filterByPriority(priority);
}

void TaskManager::totalDuration(int priority)
{
    std::vector<Task> tasks = this->serv->getAll();
    int total = 0;

    bool found = 0;
    for (Task& t : tasks)
        if (priority == t.getPriority())
            total += t.getDuration(), found = 1;
    if (found == 0)
    {
        QMessageBox::warning(this, "Warning", "There are no tasks with the given priority!");
        return;
    }
    else
        this->ui.durationLineEdit->setText(QString::fromStdString(std::to_string(total)));
}

void TaskManager::totalDurationHandler()
{
    int priority = this->ui.priorityLineEdit->text().toInt();
    this->ui.priorityLineEdit->clear();
    totalDuration(priority);
}