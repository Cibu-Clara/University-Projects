#pragma once
#include "service.h"
#include "undoService.h"

typedef struct
{
    RefrigeratorServ* serv;
    UndoService* undo_serv;
} UI;


/// <summary>
/// Constructor for the ui
/// </summary>
/// <param name="serv">The service contained by the ui</param>
/// <param name="undo_serv">The undo service contained by the ui</param>
/// <returns>The ui obj</returns>
UI* createUI(RefrigeratorServ* serv, UndoService* undo_serv);

/// <summary>
/// Destructor for the ui
/// </summary>
/// <param name="ui">The ui to be destroyed</param>
void destroyUI(UI* ui);

/// <summary>
/// The start loop
/// </summary>
/// <param name="ui">The ui</param>
void startUI(UI* ui);
