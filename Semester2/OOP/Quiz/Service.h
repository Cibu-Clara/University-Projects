#pragma once
#include "Repository.h"
#include "Observer.h"
#include <map>

class Service : public Subject
{
private:
	Repository& repo;
	std::multimap<std::string, int> answeredQuestion;

public:
	Service(Repository& repo);
	~Service();

	void addQuestion(int id, std::string text, std::string answer, int score);
	void updateParticipant(std::string name, int score);
	void addAnswer(std::string name, int id);
	bool isAnswered(std::string name, int id);

	std::vector<Participant> getParticipants();
	std::vector<Question> getQuestions();
	std::vector<Question> getSortedByScore();
};

