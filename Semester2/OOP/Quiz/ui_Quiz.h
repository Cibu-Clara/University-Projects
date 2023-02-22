/********************************************************************************
** Form generated from reading UI file 'Quiz.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_QUIZ_H
#define UI_QUIZ_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_QuizClass
{
public:
    QVBoxLayout *verticalLayout;
    QListWidget *questionsListWidget;
    QFormLayout *formLayout;
    QLineEdit *idLineEdit;
    QLabel *textLabel;
    QLineEdit *textLineEdit;
    QLabel *answerLabel;
    QLineEdit *answerLineEdit;
    QLabel *scoreLabel;
    QLineEdit *scoreLineEdit;
    QLabel *idLabel;
    QPushButton *addButton;

    void setupUi(QWidget *QuizClass)
    {
        if (QuizClass->objectName().isEmpty())
            QuizClass->setObjectName(QString::fromUtf8("QuizClass"));
        QuizClass->resize(318, 410);
        verticalLayout = new QVBoxLayout(QuizClass);
        verticalLayout->setSpacing(6);
        verticalLayout->setContentsMargins(11, 11, 11, 11);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        questionsListWidget = new QListWidget(QuizClass);
        questionsListWidget->setObjectName(QString::fromUtf8("questionsListWidget"));
        questionsListWidget->setBatchSize(100);

        verticalLayout->addWidget(questionsListWidget);

        formLayout = new QFormLayout();
        formLayout->setSpacing(6);
        formLayout->setObjectName(QString::fromUtf8("formLayout"));
        idLineEdit = new QLineEdit(QuizClass);
        idLineEdit->setObjectName(QString::fromUtf8("idLineEdit"));
        QFont font;
        font.setPointSize(12);
        idLineEdit->setFont(font);

        formLayout->setWidget(0, QFormLayout::FieldRole, idLineEdit);

        textLabel = new QLabel(QuizClass);
        textLabel->setObjectName(QString::fromUtf8("textLabel"));
        textLabel->setFont(font);

        formLayout->setWidget(1, QFormLayout::LabelRole, textLabel);

        textLineEdit = new QLineEdit(QuizClass);
        textLineEdit->setObjectName(QString::fromUtf8("textLineEdit"));
        textLineEdit->setFont(font);

        formLayout->setWidget(1, QFormLayout::FieldRole, textLineEdit);

        answerLabel = new QLabel(QuizClass);
        answerLabel->setObjectName(QString::fromUtf8("answerLabel"));
        answerLabel->setFont(font);

        formLayout->setWidget(2, QFormLayout::LabelRole, answerLabel);

        answerLineEdit = new QLineEdit(QuizClass);
        answerLineEdit->setObjectName(QString::fromUtf8("answerLineEdit"));
        answerLineEdit->setFont(font);

        formLayout->setWidget(2, QFormLayout::FieldRole, answerLineEdit);

        scoreLabel = new QLabel(QuizClass);
        scoreLabel->setObjectName(QString::fromUtf8("scoreLabel"));
        scoreLabel->setFont(font);

        formLayout->setWidget(3, QFormLayout::LabelRole, scoreLabel);

        scoreLineEdit = new QLineEdit(QuizClass);
        scoreLineEdit->setObjectName(QString::fromUtf8("scoreLineEdit"));
        scoreLineEdit->setFont(font);

        formLayout->setWidget(3, QFormLayout::FieldRole, scoreLineEdit);

        idLabel = new QLabel(QuizClass);
        idLabel->setObjectName(QString::fromUtf8("idLabel"));
        idLabel->setFont(font);

        formLayout->setWidget(0, QFormLayout::LabelRole, idLabel);


        verticalLayout->addLayout(formLayout);

        addButton = new QPushButton(QuizClass);
        addButton->setObjectName(QString::fromUtf8("addButton"));
        addButton->setFont(font);

        verticalLayout->addWidget(addButton);


        retranslateUi(QuizClass);
        QObject::connect(addButton, SIGNAL(clicked()), QuizClass, SLOT(addQuestion()));

        QMetaObject::connectSlotsByName(QuizClass);
    } // setupUi

    void retranslateUi(QWidget *QuizClass)
    {
        QuizClass->setWindowTitle(QCoreApplication::translate("QuizClass", "Presenter", nullptr));
        textLabel->setText(QCoreApplication::translate("QuizClass", "Text", nullptr));
        answerLabel->setText(QCoreApplication::translate("QuizClass", "Answer", nullptr));
        scoreLabel->setText(QCoreApplication::translate("QuizClass", "Score", nullptr));
        idLabel->setText(QCoreApplication::translate("QuizClass", "ID", nullptr));
        addButton->setText(QCoreApplication::translate("QuizClass", "Add", nullptr));
    } // retranslateUi

};

namespace Ui {
    class QuizClass: public Ui_QuizClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_QUIZ_H
