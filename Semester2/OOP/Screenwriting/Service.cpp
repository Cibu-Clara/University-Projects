#include "Service.h"

Service::Service(Repository<Writer>* writer, Repository<Idea>* ideas) :
	writers{ writer }, ideas{ ideas }
{
}

void Service::addIdea(std::string description, int act, Writer w)
{
	std::string status = "proposed";
	std::string name = w.getName();
	Idea idea(description, status, name, act);
	this->ideas->addElement(idea);
}
