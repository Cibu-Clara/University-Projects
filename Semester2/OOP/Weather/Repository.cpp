#include "Repository.h"

Repository::Repository()
{
    readData();
}

Repository::~Repository()
{
}

std::vector<Weather> Repository::getWeather() const
{
    return this->weather;
}

void Repository::readData()
{
    std::ifstream f("file.txt");
    std::string description;
    int start, end, temperature, pp_probability;
    while (f >> start >> end >> temperature >> pp_probability >> description)
    {
        Weather w(start, end, temperature, pp_probability, description);
        weather.push_back(w);
    }
}
