#include "Question.h"
#include <sstream>
#include <utility>

Question::Question():
	id{ 0 }, text{ "" }, answer{ "" }, score{ 0 }
{
}

Question::Question(int id, std::string text, std::string answer, int score) :
	id{ id }, text{ text }, answer{ answer }, score{ score }
{
}

Question::~Question()
{
}

std::string Question::toString() const
{
	return std::to_string(id) + ";" + text + ";" + answer + ";" + std::to_string(score);
}

std::string Question::toStringParticipants() const
{
	return std::to_string(id) + ";" + text + ";" + std::to_string(score);
}


std::istream& operator>>(std::istream& is, Question& q)
{
	std::string line;
	getline(is, line);
	std::vector<std::string> tokens = Question::tokenize(line, ';');

	if (tokens.size() != 4)
		return is;
	
	q.id = stoi(tokens[0]);
	q.text = tokens[1];
	q.answer = tokens[2];
	q.score = stoi(tokens[3]);

	return is;
}

std::ostream& operator<<(std::ostream& os, const Question& q)
{
	os << q.toString();
	return os;
}

std::vector<std::string> Question::tokenize(std::string str, char delimiter)
{
	std::vector<std::string> res{};
	std::stringstream s{ str };
	std::string token;
	while (getline(s, token, delimiter))
		res.push_back(token);
	return res;
}

bool Question::operator==(const Question& other) const
{
	return this->id == other.id;
}

int Question::getId() const
{
	return this->id;
}

std::string Question::getText() const
{
	return this->text;
}

std::string Question::getAnswer() const
{
	return this->answer;
}

int Question::getScore() const
{
	return this->score;
}
