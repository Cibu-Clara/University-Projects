#include "disorder.h"

Disorder::Disorder()
{
}

Disorder::Disorder(std::string category, std::string name, std::string symptoms):
	category{category}, name{name}, symptoms{symptoms}
{
}

Disorder::~Disorder()
{
}

std::string Disorder::getCategory() const
{
    return this->category;
}

std::string Disorder::getName() const
{
    return this->name;
}

std::string Disorder::getSymptoms() const
{
    return this->symptoms;
}

std::string Disorder::toString() const
{
	return category + " | " + name + " | " + symptoms;
}

std::istream& operator>>(std::istream& is, Disorder& dis)
{
    std::string line;
    getline(is, line);
    std::vector<std::string> tokens = tokenize(line, '|');

    if (tokens.size() != 3)
        return is;
    dis.category = tokens[0];
    dis.name = tokens[1];
    dis.symptoms = tokens[2];

    return is;
}

std::ostream& operator<<(std::ostream& os, const Disorder& dis)
{
	os << dis.toString();
	return os;
}