#include "Ethnologist.h"
#include "sstream"

Ethnologist::Ethnologist() {

}

Ethnologist::~Ethnologist() {

}

Ethnologist::Ethnologist(string _name, string _area) : name{ _name }, area{ _area } {

}

vector<string> Ethnologist::tokenize(const string& line, char delimiter) {
    vector<string> result;
    string token;
    stringstream ss(line);
    while (getline(ss, token, delimiter)) {
        result.push_back(token);
    }
    return result;
}

istream& operator>>(istream& input, Ethnologist& ethnologist) {
    string line;
    getline(input, line);
    vector<string> tokens = Ethnologist::tokenize(line, ';');
    if (tokens.size() != 2)
        return input;

    ethnologist.name = tokens[0];
    ethnologist.area = tokens[1];

    return input;
}

string Ethnologist::getName() {
    return this->name;
}

string Ethnologist::getArea() {
    return this->area;
}