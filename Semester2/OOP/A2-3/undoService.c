#include "undoService.h"
#include <stdlib.h>
#include <assert.h>

UndoService* createUndoService(size_t maxSize)
{
	UndoService* service = (UndoService*)malloc(sizeof(UndoService));
	if (service == NULL) return NULL;

	service->maxSize = maxSize;
	service->currentIndex = -1;
	service->currentSize = 0;
	service->maxAttainedSize = 0;

	service->history = (RefrigeratorRepo*)malloc(maxSize * sizeof(RefrigeratorRepo));
	if (service->history == NULL) return NULL;

	return service;
}

void destroyUndoService(UndoService* undoService)
{
	if (undoService == NULL) return NULL;

	for (size_t i = 0; i < undoService->currentSize; ++i)
		destroy(undoService->history[i].array);

	free(undoService->history);
	undoService->history == NULL;

	free(undoService);
}

void ReallocateHistory(UndoService* undoService, size_t newMaxSize)
{
	if (undoService->history == NULL) return NULL;
	RefrigeratorRepo* elemPtr = undoService->history;
	RefrigeratorRepo* ptr = realloc(undoService->history, newMaxSize);
	undoService->maxSize = newMaxSize;
	undoService->history = (RefrigeratorRepo*)ptr;
}

void AddEntry(UndoService* undoService, RefrigeratorRepo newEntry)
{
	if (undoService == NULL) return;
	if (undoService->history == NULL) return;

	undoService->currentIndex++;
	undoService->currentSize++;
	if (undoService->currentIndex + 1 != undoService->currentSize)
	{
		for (size_t i = undoService->currentIndex; i < undoService->currentSize - 1; ++i)
			destroy(undoService->history[i].array);

		undoService->history[undoService->currentIndex] = newEntry;
		undoService->currentSize = undoService->currentIndex + 1;
	}
	else {
		if (undoService->currentSize == undoService->maxSize)
			ReallocateHistory(undoService, 2 * undoService->currentSize);

		undoService->history[undoService->currentIndex] = newEntry;
	}
}

void RemoveEntry(UndoService* undoService, size_t position)
{
	if (undoService == NULL) return;
	if (undoService->history == NULL) return;

	if (position > undoService->currentSize) return;

	for (size_t i = position; i < undoService->currentSize - 1; ++i)
		undoService->history[i] = undoService->history[i + 1];

	undoService->currentSize--;
	undoService->currentIndex--;
}

int UndoAction(UndoService* undoService, RefrigeratorRepo* repo)
{
	if (undoService->currentIndex == 0) return 1;
	CopyRepo(undoService->history[--undoService->currentIndex], repo);
	return 0;
}

int RedoAction(UndoService* undoService, RefrigeratorRepo* repo)
{
	if (undoService->currentIndex == undoService->currentSize - 1)
		return 1;
	undoService->currentIndex++;
	CopyRepo(undoService->history[undoService->currentIndex], repo);
	return 0;
}