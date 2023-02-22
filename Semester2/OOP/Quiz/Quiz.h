#pragma once

#include <QtWidgets/QWidget>
#include "ui_Quiz.h"
#include "Service.h"
#include "Observer.h"

class Quiz : public QWidget, public Observer
{
    Q_OBJECT

public:
    Quiz(Service* serv, QWidget *parent = Q_NULLPTR);
    ~Quiz();
    void populateList();
    void update() override;

private:
    Ui::QuizClass ui;
    Service* serv;

public slots:
    void addQuestion();
};
