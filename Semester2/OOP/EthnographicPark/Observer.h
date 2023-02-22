#pragma once
#include "vector"
#include "algorithm"
using namespace std;

class Observer {
public:
    virtual void update() = 0;
    virtual ~Observer() = default;
};

class Observable {
private:
    vector<Observer*> observers;

public:
    virtual ~Observable() = default;

    void addObserver(Observer* obs)
    {
        observers.push_back(obs);
    }

    void removeObserver(Observer* obs)
    {
        auto it = std::find(this->observers.begin(), this->observers.end(), obs);
        if (it != observers.end())
            this->observers.erase(it);
    }

    void notify()
    {
        for (auto obs : observers)
        {
            obs->update();
        }
    }
};