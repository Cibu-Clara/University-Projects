#pragma once
#include <string>
#include <vector>
#include <iostream>

class HTMLTable
{
public:
	HTMLTable();

	class Cell
	{
	public:
		Cell(std::string const& data);

		friend std::ostream& operator<<(std::ostream& os, const Cell& c);
	private:
		const std::string& content;
	};

	class Row
	{
	public:
		Row();
		Row(const std::vector<std::string>& elements);

		void AddToRow(const std::string& newElement);

		friend std::ostream& operator<<(std::ostream& os, const Row& row);
	private:
		std::vector<std::string> elements;
	};


	void AddRow(const Row& newRow);

	friend std::ostream& operator<<(std::ostream& os, const HTMLTable& table);
private:
	std::vector<Row> rows;
};

