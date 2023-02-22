#pragma once
#include "string"
#include "vector"
using namespace std;

class Building {
private:
    int id;
    string description;
    string area;
    vector<string> location;

public:
    Building();
    ~Building();
    Building(int _id, string _desc, string _area, vector<string> loc);

    int getId();
    string getDescription();
    string getArea();
    vector<string> getLocation();

    void setDescription(string new_description);
    void setLocation(vector<string> new_location);

    friend istream& operator>>(istream& input, Building& building);
    static vector<string> tokenize(const string& line, char delimiter);

};
