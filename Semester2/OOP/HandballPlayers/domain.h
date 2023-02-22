#pragma once
#include<iostream>

class Player
{
private:
	std::string name;
	std::string nationality;
	std::string team;
	int score;

public:
	// default constructor for a player
	Player();

	// constructor with parameters
	Player(const std::string& name, const std::string& nationality, const std::string& team, const int score);

	// getters
	std::string getName() const;
	std::string getNationality() const;
	std::string getTeam() const;
	int getScore() const;

	// setters
	void setName(std::string new_name);
	void setNationality(std::string new_nationality);
	void setTeam(std::string new_team);
	void setScore(int new_score);

	// equality operator for the player class
	// input: other - the player to compare the current obj with
	// output: True if the players are identical, False otherwise
	bool operator==(const Player& other) const;
};

