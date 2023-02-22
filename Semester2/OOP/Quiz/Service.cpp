#include "Service.h"
#include <algorithm>

Service::Service(Repository& repo) :
	repo{ repo }
{
}

Service::~Service()
{
}

void Service::addQuestion(int id, std::string text, std::string answer, int score)
{
	Question q{ id, text, answer, score };
	this->repo.addQuestion(q);
	this->notify();
}

void Service::updateParticipant(std::string name, int score)
{
	Participant p{ name, score };
	this->repo.updateParticipant(p);
}

void Service::addAnswer(std::string name, int id)
{
	this->answeredQuestion.insert(std::pair<std::string, int>(name, id));
}

bool Service::isAnswered(std::string name, int id)
{
	for (auto& element : this->answeredQuestion)
		if (element.first == name && element.second == id)
			return true;
	return false;
}

std::vector<Participant> Service::getParticipants()
{
	return this->repo.getParticipants();
}

std::vector<Question> Service::getQuestions()
{
	return this->repo.getQuestions();
}

std::vector<Question> Service::getSortedByScore()
{
	std::vector<Question> q = this->repo.getQuestions();
	std::sort(q.begin(), q.end(), [](Question& q1, Question& q2) {return q1.getScore() > q2.getScore(); });

	return q;
}