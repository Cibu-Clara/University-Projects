#include "HTMLTable.h"

HTMLTable::Cell::Cell(std::string const& data) :
	content{ data }
{
}

HTMLTable::Row::Row() :
	elements{ std::vector<std::string>() }
{
}

HTMLTable::Row::Row(const std::vector<std::string>& elements) :
	elements{ elements }
{
}

void HTMLTable::Row::AddToRow(const std::string& newElement)
{
	elements.push_back(newElement);
}

HTMLTable::HTMLTable()
{
}

void HTMLTable::AddRow(const Row& newRow)
{
	rows.push_back(newRow);
}

std::ostream& operator<<(std::ostream& os, const HTMLTable::Cell& c)
{
	return os << "<td>" << c.content << "</td>";
}

std::ostream& operator<<(std::ostream& os, const HTMLTable::Row& row)
{
	os << "<tr>";
	for (auto& elem : row.elements)
	{
		os << HTMLTable::Cell(elem);
	}
	os << "</tr>";

	return os;
}

std::ostream& operator<<(std::ostream& os, const HTMLTable& table)
{
	os << "<table border=\"1\">";
	for (auto& elem : table.rows)
	{
		os << HTMLTable::Row(elem);
	}
	os << "</table>";

	return os;
}
