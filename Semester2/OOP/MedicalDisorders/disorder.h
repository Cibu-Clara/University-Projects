#pragma once
#include <string>
#include "utils.h"
#include<iostream>

class Disorder
{
private:
	std::string category;
	std::string name;
	std::string symptoms;

public:
	Disorder();
	Disorder(std::string category, std::string name, std::string symptoms);
	~Disorder();

	std::string getCategory() const;
	std::string getName() const;
	std::string getSymptoms() const;

	virtual std::string toString() const;
	friend std::istream& operator>>(std::istream& is, Disorder& dis);
	friend std::ostream& operator<<(std::ostream& os, const Disorder& dis);

};

