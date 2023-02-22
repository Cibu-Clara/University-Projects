#include "Writer.h"

Writer::Writer(std::string name, std::string expertise) :
    name{ name }, expertise{ expertise }
{
}

std::string Writer::getName()
{
    return this->name;
}

std::string Writer::getExpertise()
{
    return this->expertise;
}

std::ostream& operator<<(std::ostream& os, const Writer& writer)
{
    os << writer.name << " " << writer.expertise << "\n";
    return os;
}

std::istream& operator>>(std::istream& is, Writer& writer)
{
    is >> writer.name >> writer.expertise;
    return is;
}
