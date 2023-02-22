#include "Quiz.h"
#include <QMessageBox>

Quiz::Quiz(Service* serv, QWidget *parent)
    : serv(serv), QWidget(parent)
{
    ui.setupUi(this);
	this->populateList();
}

Quiz::~Quiz()
{
}

void Quiz::populateList()
{
    this->ui.questionsListWidget->clear();

	std::vector<Question> questions = this->serv->getQuestions();
	sort(questions.begin(), questions.end(), [](const Question& a, const Question& b) { return (a.getId() < b.getId()); });
	for (auto& q : questions)
	{
		this->ui.questionsListWidget->addItem(QString::fromStdString(q.toString()));
	}
}

void Quiz::update()
{
	this->populateList();
}

void Quiz::addQuestion()
{
	int id = ui.idLineEdit->text().toInt();
	std::string text = ui.textLineEdit->text().toStdString();
	std::string answer = ui.answerLineEdit->text().toStdString();
	int score = ui.scoreLineEdit->text().toInt();
	
	this->ui.idLineEdit->clear();
	this->ui.textLineEdit->clear();
	this->ui.answerLineEdit->clear();
	this->ui.scoreLineEdit->clear();

	bool duplicate = false;
	for (int i = 0; i < this->serv->getQuestions().size(); ++i)
		if (this->serv->getQuestions()[i].getId() == id) duplicate = true;
	if(duplicate)
	{
		QMessageBox::warning(this, "Warning", "A question with the same ID already exists!");
		return;
	}
	else if (text == "")
	{
		QMessageBox::warning(this, "Warning", "Please enter a valid text!");
		return;
	}
	else
		this->serv->addQuestion(id, text, answer, score);
}