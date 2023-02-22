#include "Participant.h"
#include <sstream>

Participant::Participant() :
	name{ "" }, score{ 0 }
{
}

Participant::Participant(std::string name, int score) :
	name{ name }, score{ score }
{
}

Participant::~Participant()
{
}

std::istream& operator>>(std::istream& is, Participant& p)
{
	is >> p.name >> p.score;
	return is;
}

std::ostream& operator<<(std::ostream& os, const Participant& p)
{
	os << p.name << " " << p.score << "\n";
	return os;
}

bool Participant::operator==(const Participant& other) const
{
	return this->name == other.name;
}

std::string Participant::getName() const
{
	return this->name;
}

int Participant::getScore() const
{
	return this->score;
}

void Participant::setScore(int newScore)
{
	this->score = newScore;
}
