#include "service.h"

Service::Service(Repository& repo):
	repo {repo}
{
}

Service::~Service()
{
}

std::vector<Task> Service::getAll()
{
	return this->repo.getTasks();
}

std::vector<Task> Service::getFiltered(int priority)
{
	std::vector<Task> tasks = getAll();
	std::vector<Task> filtered;
	for (Task& t : tasks)
		if (t.getPriority() == priority)
			filtered.push_back(t);
	return filtered;
}
