#pragma once

#include <QWidget>
#include "ui_ParticipantWindow.h"
#include "Service.h"
#include "Observer.h"

class ParticipantWindow : public QWidget, public Observer
{
	Q_OBJECT

public:
	ParticipantWindow(Service* serv, Participant p, QWidget* parent = Q_NULLPTR);
	~ParticipantWindow();
	void populateList();
	void update() override;
	int getSelectedIndex() const;

private:
	Ui::ParticipantWindow ui;
	Service* serv;
	Participant p;

public slots:
	void answerQuestion();
	void setAnswerButton();
};
