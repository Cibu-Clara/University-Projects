#pragma once
#include <string>

class Building {
protected:
    std::string address;
    int year;
public:
    Building();
    Building(const std::string& address, const int& year);
    ~Building();
    virtual bool must_be_restored() = 0;
    virtual bool can_be_demolished() = 0;
    virtual std::string to_string() = 0;
    int get_year();
};