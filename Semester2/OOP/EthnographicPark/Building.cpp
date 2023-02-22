#include "Building.h"

#include <utility>
#include "sstream"

Building::Building() = default;

Building::~Building() = default;

Building::Building(int _id, string _desc, string _area, vector<string> loc) : id{ _id },
description{ std::move(_desc) }, area{ std::move(_area) }, location{ std::move(loc) }{

}

int Building::getId() {
    return this->id;
}

string Building::getDescription() {
    return this->description;
}

string Building::getArea() {
    return this->area;
}

vector<string> Building::getLocation() {
    return this->location;
}

vector<string> Building::tokenize(const string & line, char delimiter) {
    vector<string> result;
    string token;
    stringstream ss(line);
    while (getline(ss, token, delimiter)) {
        result.push_back(token);
    }
    return result;
}

istream& operator>>(istream & input, Building & building) {
    string line;
    getline(input, line);
    vector<string> tokens = Building::tokenize(line, ';');
    if (tokens.size() < 4)
        return input;

    building.id = stoi(tokens[0]);
    building.description = tokens[1];
    building.area = tokens[2];
    building.location.clear();

    for (int i = 3; i < tokens.size(); i++) {
        building.location.push_back(tokens[i]);
    }

    return input;
}

void Building::setDescription(string new_description) {
    this->description = std::move(new_description);
}

void Building::setLocation(vector<string> new_location) {
    this->location = std::move(new_location);
}