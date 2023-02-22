#pragma once
#include<string>
#include<vector>

class Question
{
private:
	int id;
	std::string text;
	std::string answer;
	int score;

public:
	Question();
	Question(int id, std::string text, std::string answer, int score);
	~Question();

	virtual std::string toString() const;
	virtual std::string toStringParticipants() const;
	friend std::istream& operator>>(std::istream& is, Question& q);
	friend std::ostream& operator<<(std::ostream& os, const Question& q);
	static std::vector<std::string> tokenize(std::string str, char delimiter);
	bool operator==(const Question& other) const;

	int getId() const;
	std::string getText() const;
	std::string getAnswer() const;
	int getScore() const;
};

