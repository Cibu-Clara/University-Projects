#pragma once
#include <QWidget>
#include "ui_WriterWindow.h"
#include "Repository.h"
#include "IdeasModel.h"

class WriterWindow : public QWidget
{
	Q_OBJECT

public:
	WriterWindow(IdeaModel* model, Repository<Idea>* repo, Writer w, QWidget* parent = Q_NULLPTR);
	~WriterWindow() = default;

	void connectSignalsAndSlots();
	void selectionHandler();
	void accept();
	void developHandler();

private:
	Ui::WriterWindow ui;
	IdeaModel* model;
	Repository<Idea>* repo;
	Writer w;

public slots:
	void addHandler();
	void develop();
	void saveHandler();
};
