#include "ui.h"
#include <crtdbg.h>
#include "tests.h"
#include <stdio.h>

int main()
{
    testAll();
    RefrigeratorRepo* repo = createRepo(10);
    RefrigeratorServ* serv = createService(repo);
    UndoService* undo_serv = createUndoService(10);
    UI* ui = createUI(serv, undo_serv);

    addStart(serv, "milk", "dairy", 1.5, "12.04.2022");
    addStart(serv, "chocolate", "sweets", 0.25, "01.01.2023");
    addStart(serv, "apples", "fruit", 2, "04.05.2022");
    addStart(serv, "steak", "meat", 1.25, "25.03.2022");
    addStart(serv, "cheese", "dairy", 0.2, "26.06.2022");
    addStart(serv, "donuts", "sweets", 0.2, "31.04.2022");

    RefrigeratorRepo repoCopy = DuplicateRepo(*repo);
    AddEntry(undo_serv, repoCopy);

    startUI(ui);

    destroyUI(ui);
    destroyService(serv);
    destroyUndoService(undo_serv);
    destroyRepo(repo);
    printf("%d", _CrtDumpMemoryLeaks());

    return 0;
}