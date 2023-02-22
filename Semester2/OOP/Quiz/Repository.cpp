#include "Repository.h"
#include<fstream>

Repository::Repository()
{
	loadParticipants();
	loadQuestions();
}

Repository::~Repository()
{
}

void Repository::addQuestion(const Question& q)
{
	this->questions.push_back(q);
	writeQuestions();
}

void Repository::updateParticipant(Participant& p)
{
	auto found = std::find(this->participants.begin(), this->participants.end(), p);

	if (found != this->participants.end())
	{
		this->participants.erase(found);
		this->participants.push_back(p);
	}
	this->writeParticipants();
}

std::vector<Participant> Repository::getParticipants()
{
	return this->participants;
}

std::vector<Question> Repository::getQuestions()
{
	return this->questions;
}

void Repository::loadParticipants()
{
	std::ifstream inputFile;
	inputFile.open("participants.txt", std::ios::in);

	Participant p;
	while (inputFile >> p)
		this->participants.push_back(p);

	inputFile.close();
}

void Repository::loadQuestions()
{
	std::ifstream inputFile;
	inputFile.open("questions.txt", std::ios::in);

	Question q;
	while (inputFile >> q)
		this->questions.push_back(q);

	inputFile.close();
}

void Repository::writeParticipants()
{
	std::ofstream outputFile;
	outputFile.open("participants.txt", std::ios::out);

	for (auto& p : this->participants)
		outputFile << p;

	outputFile.close();
}

void Repository::writeQuestions()
{
	std::ofstream outputFile;
	outputFile.open("questions.txt", std::ios::out);

	for (auto& q : this->questions)
		outputFile << q << "\n";

	outputFile.close();
}
