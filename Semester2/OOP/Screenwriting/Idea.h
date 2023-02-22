#pragma once

#include <iostream>

class Idea
{
public:
	Idea() = default;
	Idea(std::string description, std::string status, std::string creator, int act);
	~Idea() = default;

	std::string getDescription();
	std::string  getStatus();
	std::string getCreator();
	int getAct();

	void setStatus(std::string newStatus);
	void setDescription(std::string newDescr);
	friend std::ostream& operator<<(std::ostream& os, const Idea& idea);
	friend std::istream& operator>>(std::istream& is, Idea& idea);

private:
	std::string description;
	std::string status;
	std::string creator;
	int act;
};
