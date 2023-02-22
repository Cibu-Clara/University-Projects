#include "Idea.h"

Idea::Idea(std::string description, std::string status, std::string creator, int act) :
    description{ description }, status{ status }, creator{ creator }, act{ act }
{
}

std::string Idea::getDescription()
{
    return this->description;
}

std::string Idea::getStatus()
{ 
    return this->status;
}

std::string Idea::getCreator()
{
    return this->creator;
}

int Idea::getAct()
{
    return this->act;
}

void Idea::setStatus(std::string newStatus)
{
    this->status = newStatus;
}

void Idea::setDescription(std::string newDescr)
{
    this->description = newDescr;
}

std::ostream& operator<<(std::ostream& os, const Idea& idea)
{
    os << idea.description << " " << idea.status << " " << idea.creator << " " << idea.act << "\n";
    return os;
}

std::istream& operator>>(std::istream& is, Idea& idea)
{
    is >> idea.description >> idea.status >> idea.creator >> idea.act;
    return is;
}
