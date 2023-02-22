#include "ParticipantWindow.h"

ParticipantWindow::ParticipantWindow(Service* serv, Participant p, QWidget *parent)
	: serv(serv), p(p), QWidget(parent)
{
	ui.setupUi(this);
	this->setWindowTitle(QString::fromStdString(this->p.getName()));
	populateList();
	this->ui.scoreLineEdit->setText(QString::number(this->p.getScore()));
	this->ui.scoreLineEdit->setEnabled(false);
}

ParticipantWindow::~ParticipantWindow()
{
}

void ParticipantWindow::populateList()
{
	this->ui.questionsListWidget->clear();

	std::vector<Question> questions = this->serv->getSortedByScore();
	for (auto& q : questions)
	{
		QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(q.toStringParticipants()));
		if (this->serv->isAnswered(p.getName(), q.getId()))
		{
			item->setBackground(Qt::green);
			this->ui.questionsListWidget->addItem(item);
		}
		else
			this->ui.questionsListWidget->addItem(item);
	}
}

void ParticipantWindow::update()
{
	this->populateList();
}

int ParticipantWindow::getSelectedIndex() const
{
	QModelIndexList index = this->ui.questionsListWidget->selectionModel()->selectedIndexes();

	if (index.size() == 0)
	{
		this->ui.answerLineEdit->clear();
		return -1;
	}

	return index.at(0).row();
}

void ParticipantWindow::answerQuestion()
{
	int selectedIndex = this->getSelectedIndex();
	if (selectedIndex != -1)
	{
		std::string answer = this->ui.answerLineEdit->text().toStdString();
		this->serv->addAnswer(this->p.getName(), this->serv->getSortedByScore()[selectedIndex].getId());
		this->ui.answerButton->setEnabled(false);
		populateList();
		this->ui.answerLineEdit->clear();
		if (answer == this->serv->getSortedByScore()[selectedIndex].getAnswer())
		{
			this->p.setScore(this->p.getScore() + this->serv->getSortedByScore()[selectedIndex].getScore());
			this->serv->updateParticipant(p.getName(), p.getScore());
			this->ui.scoreLineEdit->setText(QString::number(this->p.getScore()));
		}
	}
}

void ParticipantWindow::setAnswerButton()
{
	int selectedIndex = this->getSelectedIndex();
	if (selectedIndex != -1)
	{
		if (this->serv->isAnswered(p.getName(), this->serv->getSortedByScore()[selectedIndex].getId()))
			this->ui.answerButton->setEnabled(false);
		else
			this->ui.answerButton->setEnabled(true);
	}
}
