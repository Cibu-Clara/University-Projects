#pragma once
#include <string>
#include "Building.h"

class Block : public Building {
private:
    int total, occupied;

public:
    Block();
    Block(const std::string& address, const int& year, const int& total, const int& occupied);
    ~Block();
    bool must_be_restored() override;
    bool can_be_demolished() override;
    std::string to_string() override;
};