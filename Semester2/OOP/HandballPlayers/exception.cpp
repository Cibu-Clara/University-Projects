#include "exception.h"

RepoException::RepoException(const std::string& message) :std::exception(), message{ message } 
{}

const std::string& RepoException::getMessage() const 
{
    return this->message;
}

ValidatorException::ValidatorException(const std::string& message) : RepoException(message) 
{}