#pragma once
#include "Participant.h"
#include "Question.h"

class Repository
{
private:
	std::vector<Participant> participants;
	std::vector<Question> questions;

public:
	Repository();
	~Repository();

	void addQuestion(const Question& q);
	void updateParticipant(Participant& p);
	std::vector<Participant> getParticipants();
	std::vector<Question> getQuestions();

	void loadParticipants();
	void loadQuestions();
	void writeParticipants();
	void writeQuestions();
};

