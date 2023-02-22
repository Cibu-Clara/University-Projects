#pragma once
#include <string>

class Task
{
private:
	std::string description;
	int duration;
	int priority;

public:
	Task(std::string description, int duration, int priority);
	~Task();
	std::string getDescription() const;
	int getDuration() const;
	int getPriority() const;
	std::string to_string() const;

};

