#pragma once
#include "repository.h"

typedef struct
{
	RefrigeratorRepo* history;
	size_t maxSize;
	size_t currentIndex;
	size_t currentSize;
	size_t maxAttainedSize;
}UndoService;

/// <summary>
/// Constructor for the UndoService
/// </summary>
/// <param name="maxSize">The maximum size of the historty list</param>
/// <returns>The new UndoService object</returns>
UndoService* createUndoService(size_t maxSize);

/// <summary>
/// Destuctor for the UndoService
/// </summary>
/// <param name="undoService">The undoService object</param>
void destroyUndoService(UndoService* undoService);

/// <summary
/// Reallocates the history list of the UndoService
/// </summary>
/// <param name="undoService">The undo service</param>
/// <param name="newMaxSize">The new maximum size of the history</param>
void ReallocateHistory(UndoService* undoService, size_t newMaxSize);

/// <summary>
/// Adds a new entry to the undo service history list
/// Updates the list if the user is in the middle of an undo function
/// </summary>
/// <param name="undoService">the undo service to be modified</param>
/// <param name="newEntry">the new repository to be added</param>
void AddEntry(UndoService* undoService, RefrigeratorRepo newEntry);

/// <summary>
/// Removes form the undo service the entry located on a certain position
/// </summary>
/// <param name="undoService">The undo service to be modified</param>
/// <param name="position">The position of the element to be removed</param>
void RemoveEntry(UndoService* undoService, size_t position);

/// <summary>
/// Undoes the last action of the user
/// </summary>
/// <param name="undoService">The undoService object</param>
/// <param name="repo">The repo to be modfiedby the user</param>
/// <returns>0 if the operation was successful, 1 otherwise(there was no undo left)</returns>
int UndoAction(UndoService* undoService, RefrigeratorRepo* repo);

/// <summary>
/// Redoes the last action of the user
/// </summary>
/// <param name="undoService">The undo SErvice</param>
/// <param name="repo">The repo to be modified</param>
/// <returns>0 if the operation was made successfully, 1 otherwise(there was no redo left)</returns>
int RedoAction(UndoService* undoService, RefrigeratorRepo* repo);