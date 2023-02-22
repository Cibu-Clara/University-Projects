#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Screenwriting.h"

class Screenwriting : public QMainWindow
{
    Q_OBJECT

public:
    Screenwriting(QWidget *parent = Q_NULLPTR);

private:
    Ui::ScreenwritingClass ui;
};
