#include "WriterWindow.h"
#include <QMessageBox>

WriterWindow::WriterWindow(IdeaModel* model, Repository<Idea>* repo, Writer w, QWidget* parent)
	: model{ model }, repo{ repo }, w{ w }, QWidget(parent)
{
	ui.setupUi(this);
	ui.tableView->setModel(model);
	ui.tableView->setSortingEnabled(true);
	this->connectSignalsAndSlots();
}

void WriterWindow::connectSignalsAndSlots()
{
	QObject::connect(ui.tableView->selectionModel(), &QItemSelectionModel::selectionChanged, this, &WriterWindow::selectionHandler);
	QObject::connect(ui.tableView->selectionModel(), &QItemSelectionModel::selectionChanged, this, &WriterWindow::developHandler);
	QObject::connect(ui.acceptButton, &QPushButton::clicked, this, &WriterWindow::accept);
}

void WriterWindow::addHandler()
{
	std::string description = ui.descriptionLineEdit->text().toStdString();
	std::string act = ui.actLineEdit->text().toStdString();
	int Act = stoi(act);

	std::string status = "proposed";
	std::string creator = w.getName();

	Idea idea{ description, status, creator, Act };
	if (description == "" || (Act != 1 && Act != 2 && Act != 3))
	{
		QMessageBox::warning(this, "Warning", "Invalid description or/and act!");
		return;
	}
	else
		this->model->addIdea(idea);
}

void WriterWindow::selectionHandler()
{
	auto row = ui.tableView->currentIndex().row();
	if (row == -1) {
		ui.acceptButton->setDisabled(true);
	}
	else {
		auto idea = this->repo->getAll()[row];
		if (idea.getStatus() == "proposed" && this->w.getExpertise() == "Senior")
		{
			ui.acceptButton->setEnabled(true);
		}
		else
			ui.acceptButton->setDisabled(true);
	}
}

void WriterWindow::developHandler()
{
	auto row = ui.tableView->currentIndex().row();
	if (row == -1) {
		ui.developButton->setDisabled(true);
	}
	else {
		auto idea = this->repo->getAll()[row];
		if (idea.getStatus() == "accepted" && this->w.getName() == idea.getCreator() )
		{
			ui.developButton->setEnabled(true);
		}
		else
			ui.developButton->setDisabled(true);
	}
}

void WriterWindow::accept()
{
	auto row = ui.tableView->currentIndex().row();
	this->model->modifyStatus(row);
}


void WriterWindow::develop()
{
	auto row = ui.tableView->currentIndex().row();
	auto idea = this->repo->getAll()[row];
	this->ui.textEdit->clear();
	this->ui.textEdit->setText(QString::fromStdString(idea.getDescription()));
}

void WriterWindow::saveHandler()
{
	auto row = ui.tableView->currentIndex().row();
	std::string descr = this->ui.textEdit->toPlainText().toStdString();
	this->model->modifyDescr(row, descr);
}