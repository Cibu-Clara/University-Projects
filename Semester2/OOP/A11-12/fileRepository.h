#pragma once
#include "repository.h"

class FileRepository :
    public Repository
{
public:
    FileRepository(const std::string& path);

    void AddElement(const Movie& newMovie);
    void RemoveElemnt(size_t position);
    void UpdateTitle(size_t position, std::string newName);
    void UpdateGenre(size_t position, std::string newGenre);
    void UpdateYear(size_t position, size_t newYear);
    void UpdateLikes(size_t position, size_t newLikes);
    void UpdateTrailer(size_t position, std::string newTrailer);

protected:
    std::string filePath;
private:
    void ReadData();
    void WriteData();
};
