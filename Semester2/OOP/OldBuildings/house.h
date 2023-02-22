#pragma once
#include <string>
#include "Building.h"

class House : public Building {
private:
    bool historical;
public:
    House();
    House(const std::string& address, const int& year, bool historical);
    ~House();
    bool must_be_restored() override;
    bool can_be_demolished() override;
    std::string to_string() override;
};