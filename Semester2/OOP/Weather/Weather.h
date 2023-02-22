#pragma once
#include <string>

class Weather
{
private:
	int start;
	int end;
	int temperature;
	int pp_probability;
	std::string descripition;

public:
	Weather(int start, int end, int temperature, int pp_probability, std::string description);
	~Weather();

	int getStart() const;
	int getEnd() const;
	int getTemperature() const;
	int getPrecipitation() const;
	std::string getDescription() const;
	std::string toString() const;
};

