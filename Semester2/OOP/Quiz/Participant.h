#pragma once
#include<string>

class Participant
{
private:
	std::string name;
	int score;

public:
	Participant();
	Participant(std::string name, int score);
	~Participant();

	friend std::istream& operator>>(std::istream& is, Participant& p);
	friend std::ostream& operator<<(std::ostream& os, const Participant& p);
	bool operator==(const Participant& other) const;

	std::string getName() const;
	int getScore() const;
	void setScore(int newScore);
};

