#include "domain.h"

Player::Player() :
	name{ "" }, nationality{ "" }, team{ ""}, score{0}
{}

Player::Player(const std::string& name, const std::string& nationality, const std::string& team, int score) 
{
	this->name = name;
	this->nationality = nationality;
	this->team = team;
	this->score = score;
}

std::string Player::getName() const
{
	return this->name;
}

std::string Player::getNationality() const
{
	return this->nationality;
}

std::string Player::getTeam() const
{
	return this->team;
}

int Player::getScore() const
{
	return this->score;
}

void Player::setName(std::string new_name)
{
	this->name = new_name;
}

void Player::setNationality(std::string new_nationality)
{
	this->nationality = new_nationality;
}

void Player::setTeam(std::string new_team)
{
	this->team = new_team;
}

void Player::setScore(int new_score)
{
	this->score = new_score;
}

bool Player::operator==(const Player& other) const
{
	return this->name == other.name;
}
