#include "Service.h"

Service::Service(Repository& repo) : repo(repo)
{
}

Service::~Service()
{
}

std::vector<Weather> Service::getAll()
{
	return this->repo.getWeather();
}

std::vector<Weather> Service::getFiltered(int pp)
{
	std::vector<Weather> weather = getAll();
	std::vector<Weather> filtered;
	for (Weather& w : weather)
		if (w.getPrecipitation() <= pp)
			filtered.push_back(w);
	return filtered;
}
