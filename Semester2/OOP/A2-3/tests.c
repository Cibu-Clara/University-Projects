#include "tests.h"
#include "domain.h"
#include "repository.h"
#include "service.h"
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include "undoService.h"

void testDomain()
{
    Product prod = createProduct("bananas", "fruit", 2, "30.05.2022");
    assert(strcmp(getName(prod), "bananas") == 0);
    assert(strcmp(getCategory(prod), "fruit") == 0);
    assert(getQuantity(prod) == 2);
    assert(strcmp(getDate(prod), "30.05.2022") == 0);
}

void testRepo()
{
    RefrigeratorRepo* repo = createRepo(10);

    Product p1 = createProduct("bananas", "fruit", 2, "30.05.2022");
    addProduct(repo, p1);
    assert(getRepoLength(repo) == 1);
    assert(strcmp(getName(getProductOnPos(repo, 0)), "bananas") == 0);

    Product p2 = createProduct("lollipop", "sweets", 0.1, "03.03.2025");
    assert(addProduct(repo, p2) == 1);
    assert(getRepoLength(repo) == 2);

    // now try to add the same product again -> add must return 0
    assert(addProduct(repo, p2) == 0);

    assert(findProduct(repo, "bananas", "fruit") == 0);

    deleteProduct(repo, "bananas", "fruit");
    assert(findProduct(repo, "bananas", "fruit") == -1);
    assert(getRepoLength(repo) == 1);

    // destroy the test repository
    destroyRepo(repo);
}
 
void testServ() {
    RefrigeratorRepo* repo = createRepo(1);
    RefrigeratorServ* serv = createService(repo);
    assert(addServ(serv, "test", "test", 12, "test") == 1);
    assert(updateServ(serv, "test", "test", "testttt", "testing", 50, "test") == 1);
    assert(addServ(serv, "test", "test", 12, "test") == 1);
    assert(delServ(serv, "test", "test") == 1);
    assert(updateServ(serv, "test", "test", "testttt", "testing", 50, "test") == -1);
    assert(delServ(serv, "test", "testing") == -1);
    
    assert(getNrOfDays(30, 5, 2022, 15, 5, 2022) == 15);

    Product p = createProduct("test", "test", 12, "02.07.2023");
    assert(getExpirationDay(serv, p) == 2);
    assert(getExpirationMonth(serv, p) == 7);
    assert(getExpirationYear(serv, p) == 2023);

    destroyService(serv);
    destroyRepo(repo);
}

void testAll()
{
    testDomain();
    testRepo();
    testServ();
}
