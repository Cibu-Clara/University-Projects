#include "service.h"
#include "validator.h"

Service::Service( Repository& r) :
	repo{ r }
{
}

Repository Service::getRepo() const
{
	return this->repo;
}

void Service::addServ(const std::string& name, const std::string& nationality, const std::string& team, int score)
{
	Player p{ name, nationality, team, score };
	PlayerValidator::validate(p);
	this->repo.addPlayer(p);
}

void Service::removeServ(std::string name)
{
	int pos = this->repo.findByName(name);
	
	this->repo.removePlayer(pos);
}

void Service::initializeRepo()
{
	addServ("Nora_Mork", "NOR", "Larvik", 83);
	addServ("Isabelle_Gulden", "SWE", "CSM_Bucuresti", 80);
	addServ("Cristina_Neagu", "ROU", "Buducnost", 63);
	addServ("Allison_Pineau", "FRA", "HCM_Baia_Mare", 82);
	addServ("Ilina_Ekaterina", "RUS", "Rostov-Don", 80);
	addServ("Nerea_Pena", "ESP", "FTC_Rail_Cargo_Hungaria", 59);
}