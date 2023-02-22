#include "Weather.h"

Weather::Weather(int start, int end, int temperature, int pp_probability, std::string description) : start(start), end(end),
temperature(temperature), pp_probability(pp_probability), descripition(description)
{
}

Weather::~Weather()
{
}

int Weather::getStart() const
{
	return this->start;
}

int Weather::getEnd() const
{
	return this->end;
}

int Weather::getTemperature() const
{
	return this->temperature;
}

int Weather::getPrecipitation() const
{
	return this->pp_probability;
}

std::string Weather::getDescription() const
{
	return this->descripition;
}

std::string Weather::toString() const
{
	return std::to_string(start) + ";" + std::to_string(end) + ";" + std::to_string(temperature) + ";" + std::to_string(pp_probability) + ";" + descripition;
}
