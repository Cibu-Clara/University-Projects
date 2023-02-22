#pragma once
#include <vector>
#include "Building.h"

class Service {
private:
    std::vector<Building*> build;
public:
    Service();
    ~Service();
    void add_a_building(Building* a);
    std::vector<Building*> get_all_buildings();
    std::vector<Building*> get_all_to_be_restored();
    std::vector<Building*> get_all_to_be_demolished();
    void write_to_file(std::string file, std::vector<Building*> buildings);
};