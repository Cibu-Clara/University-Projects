#include "tests.h"
#include "domain.h"
#include "dynamicVector.h"
#include "repository.h"
#include "userService.h"
#include "adminService.h"
#include <assert.h>

void Tests::TestAll()
{
	TestConstructorsAdminServ();
	TestAdd();
	TestRemove();
	TestUpdate();

	TestConstructors();
	TestAdding();
	TestRemovingAndSubscript();
	TestAssignmentAndEquality();

	TestConstructorsAndGettersMovie();
	TestSettersMovie();
	TestEqualityMovie();
	TestExtractionOperator();

	TestConstructorsAndAssignment();
	TestAddRepo();
	TestRemoveSubscript();
	TestUpdateRepo();
	TestFind();
	TestPrint();

	TestConstructorUserServ();
	TestSkippingAndAdoption();
	TestFilteringAndReinitialization();
	TestGetters();
}

void Tests::TestConstructorsAdminServ()
{
	Repository repo(20);
	AdminService s2(repo);
	assert(s2.repo.GetSize() == 0);
	assert(s2.GetRepo() == repo);
}

void Tests::TestAdd()
{
	Repository repo(20);
	AdminService s2(repo);

	s2.AddMovie("Shutter_Island", "psychological", 2019, 1009, "https://google.ro");
	assert(s2.repo[0].GetTitle() == "Shutter_Island");
	assert(s2.repo[0].GetLikes() == 1009);
	assert(s2.repo[0].GetGenre() == "psychological");
	assert(s2.repo[0].GetYear() == 2019);
	assert(s2.repo[0].GetTrailer() == "https://google.ro");

	s2.AddMovie("Anabelle", "Horror", 2020, 2993, "https://google.ro");
	assert(s2.repo[1].GetTitle() == "Anabelle");
	assert(s2.repo[1].GetLikes() == 2993);
	assert(s2.repo[1].GetGenre() == "Horror");
	assert(s2.repo[1].GetYear() == 2020);
	assert(s2.repo[1].GetTrailer() == "https://google.ro");

}

void Tests::TestRemove()
{
	Repository repo(20);
	AdminService s2(repo);
	s2.InitializeRepo();
	assert(s2.repo[0].GetTitle() == "Titanic");
	assert(s2.repo[0].GetLikes() == 55000);
	assert(s2.repo[0].GetGenre() == "Romance");
	assert(s2.repo[0].GetYear() == 1997);
	assert(s2.repo[0].GetTrailer() == "https://www.youtube.com/watch?v=kVrqfYjkTdQ");

	s2.RemoveMovie("Titanic");
	assert(s2.repo[0].GetTitle() == "Sinister");
	assert(s2.repo[0].GetLikes() == 2110);
	assert(s2.repo[0].GetGenre() == "Horror");
	assert(s2.repo[0].GetYear() == 2012);
	assert(s2.repo[0].GetTrailer() == "https://www.youtube.com/watch?v=_kbQAJR9YWQ");

}

void Tests::TestUpdate()
{
	Repository repo(20);
	AdminService s2(repo);
	s2.InitializeRepo();
	assert(s2.repo[0].GetTitle() == "Titanic");
	assert(s2.repo[0].GetLikes() == 55000);
	assert(s2.repo[0].GetGenre() == "Romance");
	assert(s2.repo[0].GetYear() == 1997);
	assert(s2.repo[0].GetTrailer() == "https://www.youtube.com/watch?v=kVrqfYjkTdQ"); 

	s2.UpdateMovieTitle("Sinister", "Avengers");
	assert(s2.repo[1].GetTitle() == "Avengers");

	s2.UpdateMovieGenre("Avengers", "Adventure");
	s2.UpdateMovieLikes("Avengers", 290);
	s2.UpdateMovieYear("Avengers", 2020);
	s2.UpdateMovieTrailer("Avengers", "https://dog.ro");


	assert(s2.repo[1].GetLikes() == 290);
	assert(s2.repo[1].GetGenre() == "Adventure");
	assert(s2.repo[1].GetYear() == 2020);
	assert(s2.repo[1].GetTrailer() == "https://dog.ro");
}

void Tests::TestConstructors()
{
	DynamicVector<int> v1;
	assert(v1.currentSize == 0);
	assert(v1.maxSize == 0);
	assert(v1.elements == nullptr);

	DynamicVector<int> v2(10);
	assert(v2.currentSize == 0);
	assert(v2.maxSize == 10);
	assert(v2.elements != nullptr);
}

void Tests::TestAdding()
{
	DynamicVector<int> v1(1);
	v1.AddElement(2);
	assert(v1.currentSize == 1);
	v1.AddElement(3);
	assert(v1.currentSize == 2);
	assert(v1.maxSize == 2);
}

void Tests::TestRemovingAndSubscript()
{
	DynamicVector<int> v1(1);
	v1.AddElement(2);
	v1.AddElement(3);
	v1.AddElement(4);
	v1.AddElement(5);
	v1.AddElement(6);
	v1.RemoveElement(0);
	assert(v1[0] == 3);
	v1.RemoveElement(1);
	assert(v1[1] == 5);

}

void Tests::TestAssignmentAndEquality()
{
	DynamicVector<int> v1(1);
	v1.AddElement(2);
	v1.AddElement(3);
	v1.AddElement(4);
	v1.AddElement(5);
	v1.AddElement(6);
	DynamicVector<int> v2(8);

	v2 = v1;
	assert(v1 == v2);
	v2.RemoveElement(0);
	v2.AddElement(1);
	assert(!(v1 == v2));
}

void Tests::TestConstructorsAndGettersMovie()
{
	Movie d1;
	assert(d1.GetTitle() == "");
	assert(d1.GetGenre() == "");
	assert(d1.GetYear() == 0);
	assert(d1.GetLikes() == 0);
	assert(d1.GetTrailer() == "");

	Movie d2("A", "B", 2, 3, "google.com");
	assert(d2.GetTitle() == "A");
	assert(d2.GetGenre() == "B");
	assert(d2.GetYear() == 2);
	assert(d2.GetLikes() == 3);
	assert(d2.GetTrailer() == "google.com");
}

void Tests::TestSettersMovie()
{
	Movie d2;
	d2.SetTitle("A");
	d2.SetGenre("B");
	d2.SetYear(2002);
	d2.SetLikes(3);
	d2.SetTrailer("google.com");

	assert(d2.GetTitle() == "A");
	assert(d2.GetGenre() == "B");
	assert(d2.GetYear() == 2002);
	assert(d2.GetLikes() == 3);
	assert(d2.GetTrailer() == "google.com");

}

void Tests::TestEqualityMovie()
{
	Movie d1;
	d1.SetTitle("A");
	d1.SetGenre("B");
	d1.SetYear(2002);
	d1.SetLikes(3);
	d1.SetTrailer("google.com");

	Movie d2;
	d2.SetTitle("A");
	d2.SetGenre("B");
	d2.SetYear(2002);
	d2.SetLikes(3);
	d2.SetTrailer("google.com");

	assert(d1 == d2);
	d2.SetTitle("Gigel");
	assert(!(d1 == d2));
}

void Tests::TestExtractionOperator()
{
	Movie d1;
	d1.SetTitle("Ion");
	d1.SetGenre("Something");
	d1.SetYear(1980);
	d1.SetLikes(100);
	d1.SetTrailer("site.ro");
	std::stringbuf buffer;
	std::ostream os(&buffer);
	os << d1;
	assert(buffer.str() == "Movie: Ion | Genre: Something | Year: 1980 | Nr. of likes: 100 | Trailer: site.ro\n");
}

void Tests::TestConstructorsAndAssignment()
{
	Repository repo1;
	assert(repo1.elementsArray == nullptr);

	Repository repo2(10);
	assert(repo2.elementsArray != nullptr);
	assert(repo2.GetSize() == 0);

	repo2.AddElement(Movie("A", "B", 1900, 100, "google.ro"));
	repo1 = repo2;

	assert(*repo1.elementsArray == *repo2.elementsArray);
}

void Tests::TestAddRepo()
{
	Repository repo2(10);
	Movie d1("Idk", "Idk", 1, 2, "google.com");
	Movie d2("Idk", "Idk1", 1, 2, "google.com");
	Movie d3("Idk", "Idk2", 1, 2, "google.com");
	Movie d4("Idk", "Idk3", 1, 2, "google.com");
	repo2.AddElement(d1);
	repo2.AddElement(d2);
	repo2.AddElement(d3);
	repo2.AddElement(d4);

	assert(repo2[0] == d1);
	assert(repo2[1] == d2);
	assert(repo2[2] == d3);
	assert(repo2[3] == d4);
}

void Tests::TestRemoveSubscript()
{
	Repository repo2(10);
	Movie d1("Idk", "Idk", 1, 2, "google.com");
	Movie d2("Idk", "Idk1", 1, 2, "google.com");
	Movie d3("Idk", "Idk2", 1, 2, "google.com");
	Movie d4("Idk", "Idk3", 1, 2, "google.com");
	repo2.AddElement(d1);
	repo2.AddElement(d2);
	repo2.AddElement(d3);
	repo2.AddElement(d4);

	repo2.RemoveElemnt(1);

	assert(repo2[0] == d1);
	assert(repo2[1] == d3);
	assert(repo2[2] == d4);
}

void Tests::TestUpdateRepo()
{
	Repository repo2(10);
	Movie d1("Idk", "Idk", 1, 2, "google.com");
	repo2.AddElement(d1);

	repo2.UpdateTitle(0, "A");
	repo2.UpdateGenre(0, "B");
	repo2.UpdateYear(0, 2002);
	repo2.UpdateLikes(0, 209);
	repo2.UpdateTrailer(0, "google.ro");
	assert(repo2[0].GetTitle() == "A");
	assert(repo2[0].GetGenre() == "B");
	assert(repo2[0].GetYear() == 2002);
	assert(repo2[0].GetLikes() == 209);
	assert(repo2[0].GetTrailer() == "google.ro");

}

void Tests::TestFind()
{
	Repository repo2(10);
	Movie d1("Idk", "Idk", 1, 2, "google.com");
	Movie d2("Idk", "Idk1", 1, 2, "google.com");
	Movie d3("Idkk", "Idk2", 1, 2, "google.com");
	Movie d4("Idk", "Idk3", 1, 2, "google.com");
	repo2.AddElement(d1);
	repo2.AddElement(d2);
	repo2.AddElement(d3);
	repo2.AddElement(d4);

	size_t position = repo2.FindElemByTitle("Idkk");
	assert(position == 2);
}

void Tests::TestPrint()
{
	Repository repo(10);
	Movie d1("Ion", "Something", 1980, 100, "site.ro");
	std::stringbuf buffer;
	std::ostream os(&buffer);
	os << d1;
	assert(buffer.str() == "Movie: Ion | Genre: Something | Year: 1980 | Nr. of likes: 100 | Trailer: site.ro\n");
}

void Tests::TestConstructorUserServ()
{
	Repository repo(20);
	repo.AddElement(Movie("Sinister", "Horror", 2010, 100, "https://google.ro"));
	repo.AddElement(Movie("JurasicPark", "Adventure", 2011, 210, "https://google.ro"));
	repo.AddElement(Movie("Flowers", "Horror", 2013, 1000, "https://google.ro"));
	repo.AddElement(Movie("Mermaid", "Romance", 2018, 3000, "https://google.ro"));
	repo.AddElement(Movie("Sinner", "Drama", 2017, 4000, "https://google.ro"));
	repo.AddElement(Movie("Anabelle", "Horror", 2020, 2993, "https://google.ro"));
	repo.AddElement(Movie("Beatiful", "Romance", 2016, 4002, "https://google.ro"));
	repo.AddElement(Movie("Christmas", "Horror", 2022, 4563, "https://google.ro"));
	repo.AddElement(Movie("Mama", "Horror", 2013, 10024, "https://google.ro"));
	repo.AddElement(Movie("Home", "Horror", 2015, 3000, "https://google.ro"));
	UserService s1(repo);
	assert(s1.CurrentList == repo.GetArray());
	assert(s1.index == 0);
	assert(s1.WatchList.GetSize() == 0);
}

void Tests::TestSkippingAndAdoption()
{
	Repository repo(20);
	repo.AddElement(Movie("Sinister", "Horror", 2010, 100, "https://google.ro"));
	repo.AddElement(Movie("JurasicPark", "Adventure", 2011, 210, "https://google.ro"));
	UserService s1(repo);

	s1.AddToWatchList(s1.repo[s1.index]);
	assert(s1.WatchList.GetSize() == 1);
	assert(s1.WatchList[0] == s1.repo[0]);
	assert(s1.index == 1);

	s1.GoToNextMovie();
	assert(s1.index == 0);
}

void Tests::TestFilteringAndReinitialization()
{
	Repository repo(20);
	repo.AddElement(Movie("Sinister", "Horror", 2010, 100, "https://google.ro"));
	repo.AddElement(Movie("JurasicPark", "Adventure", 2011, 210, "https://google.ro"));
	repo.AddElement(Movie("Flowers", "Horror", 2013, 1000, "https://google.ro"));
	repo.AddElement(Movie("Mermaid", "Romance", 2018, 3000, "https://google.ro"));
	repo.AddElement(Movie("Sinner", "Drama", 2017, 4000, "https://google.ro"));
	repo.AddElement(Movie("Anabelle", "Horror", 2020, 2993, "https://google.ro"));
	repo.AddElement(Movie("Beatiful", "Romance", 2016, 4002, "https://google.ro"));
	repo.AddElement(Movie("Christmas", "Horror", 2022, 4563, "https://google.ro"));
	repo.AddElement(Movie("Mama", "Horror", 2013, 10024, "https://google.ro"));
	repo.AddElement(Movie("Home", "Horror", 2015, 3000, "https://google.ro"));
	UserService s1(repo);

	s1.FilterByGenre("Horror");
	assert(s1.CurrentList.GetSize() == 6);
	assert(s1.CurrentList[0] == Movie("Sinister", "Horror", 2010, 100, "https://google.ro"));
	assert(s1.CurrentList[1] == Movie("Flowers", "Horror", 2013, 1000, "https://google.ro"));

	s1.ReinitializeMovieList();
	assert(s1.CurrentList.GetSize() == 10);
	assert(s1.index == 0);

	s1.FilterByGenre("");
	assert(s1.CurrentList.GetSize() == 10);

	s1.ReinitializeMovieList();
	assert(s1.CurrentList.GetSize() == 10);
	assert(s1.index == 0);

	s1.ReinitializeMovieList();
	assert(s1.CurrentList.GetSize() == 10);
	assert(s1.index == 0);

	s1.WatchList.AddElement(Movie("Sinister", "Horror", 2002, 100, "https://google.ro"));
	s1.WatchList.AddElement(Movie("Flowers", "Horror", 2013, 1000, "https://google.ro"));
	assert(s1.WatchList[0].GetTitle() == "Sinister");
	assert(s1.WatchList.GetSize() == 2);
	s1.DeleteMovieWatchList("Sinister", 10);
	assert(s1.WatchList.GetSize() == 1);
	assert(s1.WatchList[0].GetTitle() == "Flowers");

	bool found = false;
	size_t index = 0;
	for (size_t j = 0; j < s1.CurrentList.GetSize() && !found; j++)
		if (s1.CurrentList[j].GetTitle() == "Sinister")
		{
			found = true;
			assert(s1.CurrentList[j].GetLikes() == 110);
			index = j;
		}
}

void Tests::TestGetters()
{
	Repository repo(20);
	repo.AddElement(Movie("Mermaid", "Romance", 2018, 3000, "https://google.ro"));
	repo.AddElement(Movie("Mama", "Horror", 2013, 10024, "https://google.ro"));
	UserService s1(repo);

	s1.AddToWatchList(s1.repo[s1.index]);

	s1.GoToNextMovie();

	assert(s1.GetMovieList() == s1.WatchList);
	assert(s1.GetCurrentMovie() == Movie("Mermaid", "Romance", 2018, 3000, "https://google.ro"));
	try
	{
		s1.AddToWatchList(s1.repo[s1.index]);
		assert(false);
	}
	catch (std::exception& ex)
	{
		assert(true);
	}
}