#include "Repository.h"

#include <utility>
#include "fstream"

Repository::Repository() {
    this->load_buildings();
    this->load_ethnologists();
    this->availableID = this->buildings.size() + 1;
}

Repository::~Repository() {

}

vector<Ethnologist>& Repository::get_ethnologists() {
    return this->ethnologists;
}

vector<Building>& Repository::get_buildings() {
    return this->buildings;
}

void Repository::load_ethnologists() {
    ifstream in_file("ethnologists.txt");
    this->ethnologists.clear();
    Ethnologist ethnologist;
    while (in_file >> ethnologist) {
        this->ethnologists.push_back(ethnologist);
    }
    in_file.close();
}

void Repository::load_buildings() {
    ifstream in_file("buildings.txt");
    this->buildings.clear();
    Building building;
    while (in_file >> building) {
        this->buildings.push_back(building);
    }
    in_file.close();
}

void Repository::addBuilding(const string& description, const string& area, vector<string> square_location) {
    int id = this->availableID;
    this->availableID++;
    Building newBuilding(id, description, area, std::move(square_location));
    this->buildings.push_back(newBuilding);
}

void Repository::updateBuilding(int id, const string& description, const vector<string>& location) {
    for (auto& building : this->buildings) {
        if (building.getId() == id) {
            building.setDescription(description);
            building.setLocation(location);
        }
    }
}