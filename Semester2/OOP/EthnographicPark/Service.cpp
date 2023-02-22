#include "Service.h"
#include "sstream"

Service::Service(Repository& repository1) : repository{ repository1 } {

}

Service::~Service() = default;

vector<Ethnologist>& Service::get_ethnologists() {
    return this->repository.get_ethnologists();
}

vector<Building>& Service::get_buildings() {
    return this->repository.get_buildings();
}

void Service::addBuilding(const string & description, const string & area, const string & location) {
    vector<string> square_locations = this->tokenizeLocation(location);
    bool overlaps = this->checkOverlaps(square_locations, 0);
    if (overlaps) {
        throw runtime_error("The building overlaps with another building!");
    }

    bool contiguousArea = this->checkContiguousArea(square_locations);
    if (!contiguousArea) {
        throw runtime_error("The building location is not in a contiguous area");
    }
    this->repository.addBuilding(description, area, square_locations);
    this->notify();
}

vector<string> Service::tokenizeLocation(const string & location) {
    vector<string> result;
    string token;
    stringstream ss(location);
    while (getline(ss, token, ' ')) {
        result.push_back(token);
    }
    return result;
}

bool Service::checkOverlaps(vector<string> squareLocations, int id) {
    vector<Building> allBuildings = this->get_buildings();
    for (auto building : allBuildings) {
        if (building.getId() != id) {
            for (auto square : building.getLocation()) {
                if (std::find(squareLocations.begin(), squareLocations.end(), square) != squareLocations.end()) {
                    return true;
                }
            }
        }
    }
    return false;
}

bool Service::checkContiguousArea(vector<string> squareLocations) {
    for (int i = 0; i < squareLocations.size() - 1; i++) {
        string location1 = squareLocations[i];
        string location2 = squareLocations[i + 1];
        if (!((location1[0] == location2[0] && location1[1] != location2[1])
            || (location1[1] == location2[1] && location1[0] != location2[0]))) {
            return false;
        }
    }
    return true;
}

void Service::updateBuilding(int id, const string & description, const string & location) {
    vector<string> square_locations = this->tokenizeLocation(location);
    bool overlaps = this->checkOverlaps(square_locations, id);
    if (overlaps) {
        throw runtime_error("The building overlaps with another building!");
    }

    bool contiguousArea = this->checkContiguousArea(square_locations);
    if (!contiguousArea) {
        throw runtime_error("The building location is not in a contiguous area");
    }
    this->repository.updateBuilding(id, description, square_locations);
    this->notify();
}

vector<Building> Service::getSortedByArea(Ethnologist ethnologist) {
    vector<Building> sortedBuildingsFinal;
    for (auto& building : this->get_buildings())
        if (building.getArea() == ethnologist.getArea())
            sortedBuildingsFinal.push_back(building);

    vector<Building> allBuildingsSorted(this->get_buildings());
    for (int i = 0; i < allBuildingsSorted.size(); i++)
        for (int j = i + 1; j < allBuildingsSorted.size() - 1; j++)
            if (allBuildingsSorted[i].getArea() > allBuildingsSorted[j].getArea())
                swap(allBuildingsSorted[i], allBuildingsSorted[j]);

    for (auto& building : allBuildingsSorted)
        if (building.getArea() != ethnologist.getArea() && building.getArea() != "office")
            sortedBuildingsFinal.push_back(building);

    for (auto& b : allBuildingsSorted)
        if (b.getArea() == "office")
            sortedBuildingsFinal.push_back(b);

    return sortedBuildingsFinal;
}