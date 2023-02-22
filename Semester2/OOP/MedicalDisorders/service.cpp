#include "service.h"

Service::Service(Repository& repo): repo{repo}
{
}

Service::~Service()
{
}

std::vector<Disorder> Service::getAll()
{
	return this->repo.getDisorders();
}

std::vector<Disorder> Service::query(std::string q)
{
    std::vector <Disorder> res;
    std::vector<Disorder> dis = getAll();
    for (auto& i : dis) {
        auto found = i.getName().find(q);
        auto found2 = i.getCategory().find(q);
        if (found != std::string::npos || found2 != std::string::npos) {
            res.push_back(i);
        }
    }
    return res;
}