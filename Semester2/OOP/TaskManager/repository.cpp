#include "repository.h"

Repository::Repository()
{
	readData();
}

Repository::~Repository()
{
}

std::vector<Task> Repository::getTasks() const
{
    return this->tasks;
}

void Repository::readData()
{
    std::ifstream f("file.txt");
    std::string description;
    int duration, priority;
    while (f >> description >> duration >> priority)
    {
        Task t(description, duration, priority);
        tasks.push_back(t);
    }
}
