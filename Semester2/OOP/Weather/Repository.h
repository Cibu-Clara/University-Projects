#pragma once
#include "Weather.h"
#include <vector>
#include <fstream>

class Repository
{
public:
	Repository();
	~Repository();
	std::vector<Weather> getWeather() const;

private:
	std::vector<Weather> weather;
	void readData();
};

