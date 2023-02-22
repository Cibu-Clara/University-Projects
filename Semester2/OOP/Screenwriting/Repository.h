#pragma once
#include "Writer.h"
#include "Idea.h"
#include <fstream>
#include <vector>
#include <iostream>

template<typename T>
class Repository
{
public:
	Repository() = default;
	Repository(std::string& filePath);
	~Repository() = default;

	void addElement(T& elem);
	std::vector<T> getAll();
	int getSize();
	void clear();
	void updateStatus(int row);
	void updateDescription(int row, std::string descr);

private:
	std::vector<T> data;
	std::string filePath;

	void writeToFile();
	void readFromFile();
};

template<typename T>
inline Repository<T>::Repository(std::string& filePath) :
	filePath{ filePath }
{
	this->readFromFile();
}

template<typename T>
inline void Repository<T>::addElement(T& elem)
{
	this->data.push_back(elem);
	this->writeToFile();
}

template<typename T>
inline std::vector<T> Repository<T>::getAll()
{
	return this->data;
}

template<typename T>
inline int Repository<T>::getSize()
{
	return this->data.size();
}

template<typename T>
inline void Repository<T>::clear()
{
	this->data.clear();
}

template<typename T>
inline void Repository<T>::updateStatus(int row)
{
	this->data[row].setStatus("accepted");
	this->writeToFile();
}

template<typename T>
inline void Repository<T>::updateDescription(int row, std::string descr)
{
	this->data[row].setDescription(descr);
	this->writeToFile();
}

template<typename T>
inline void Repository<T>::writeToFile()
{
	std::ofstream outputFile;
	outputFile.open(this->filePath, std::ios::out);

	for (auto obj : this->data)
		outputFile << obj;

	outputFile.close();
}

template<typename T>
inline void Repository<T>::readFromFile()
{
	std::ifstream inputFile;
	inputFile.open(this->filePath, std::ios::in);

	T elem;
	while (inputFile >> elem)
		this->data.push_back(elem);

	inputFile.close();
}
