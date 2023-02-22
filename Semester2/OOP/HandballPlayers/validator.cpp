#include "validator.h"
#include "exception.h"
#include <string>

bool PlayerValidator::validate(const Player& p) {
    std::string errors = "";
    if (p.getName() == "")
    {
        errors += "Invalid name!\n";
    }
    if (p.getNationality() == "")
    {
        errors += "Invalid nationality!\n";
    }
    if (p.getNationality().size() != 3)
    {
        errors += "Nationality must be a 3-letter abbreviation!\n";
    }
    if (p.getTeam() == "")
    {
        errors += "Invalid team!\n";
    }
    if (p.getScore() < 0)
    {
        errors += "Invalid score!\n";
    }
    if (errors.size() > 0)
    {
        throw ValidatorException(errors);
    }
}