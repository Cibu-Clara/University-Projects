#pragma once
#include <vector>

class Observer
{
public:
	virtual void update() = 0;
	~Observer() = default;
};

class Subject
{
public:
	void addObserver(Observer* observer) {
		this->observers.push_back(observer);
	}
	
	void deleteObserver(Observer* observer) {
		auto it = std::find(this->observers.begin(), this->observers.end(), observer);
		if (it != this->observers.end())
			this->observers.erase(it);
	}

	void notify() {
		for (auto observer : this->observers)
			observer->update();
	}

private:
	std::vector<Observer*> observers;
};
