#pragma once
#include <iostream>

class Writer
{
public:
	Writer() = default;
	Writer(std::string name, std::string expertise);
	~Writer() = default;

	friend std::ostream& operator<<(std::ostream& os, const Writer& writer);
	friend std::istream& operator>>(std::istream& is, Writer& writer);

	std::string getName();
	std::string getExpertise();

private:
	std::string name;
	std::string expertise;
};

