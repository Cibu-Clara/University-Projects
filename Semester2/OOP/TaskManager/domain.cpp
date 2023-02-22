#include "domain.h"

Task::Task(std::string description, int duration, int priority):
	description{description}, duration{duration}, priority{priority}
{
}

Task::~Task()
{
}

std::string Task::getDescription() const
{
	return this->description;
}

int Task::getDuration() const
{
	return this->duration;
}

int Task::getPriority() const
{
	return this->priority;
}

std::string Task::to_string() const
{
	return description + " | " + std::to_string(duration) + " | " + std::to_string(priority);
}
