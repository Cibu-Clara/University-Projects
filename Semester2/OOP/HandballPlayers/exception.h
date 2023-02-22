#pragma once
#include <exception>
#include <string>

class RepoException :public std::exception {
private:
    std::string message;
public:
    RepoException(const std::string&);
    const std::string& getMessage() const;

};

class ValidatorException : public RepoException {
public:
    ValidatorException(const std::string&);
};