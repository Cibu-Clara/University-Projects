/********************************************************************************
** Form generated from reading UI file 'ParticipantWindow.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_PARTICIPANTWINDOW_H
#define UI_PARTICIPANTWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ParticipantWindow
{
public:
    QVBoxLayout *verticalLayout;
    QListWidget *questionsListWidget;
    QHBoxLayout *horizontalLayout_3;
    QHBoxLayout *horizontalLayout;
    QLineEdit *answerLineEdit;
    QPushButton *answerButton;
    QHBoxLayout *horizontalLayout_2;
    QLabel *label;
    QLineEdit *scoreLineEdit;

    void setupUi(QWidget *ParticipantWindow)
    {
        if (ParticipantWindow->objectName().isEmpty())
            ParticipantWindow->setObjectName(QString::fromUtf8("ParticipantWindow"));
        ParticipantWindow->resize(276, 304);
        verticalLayout = new QVBoxLayout(ParticipantWindow);
        verticalLayout->setSpacing(6);
        verticalLayout->setContentsMargins(11, 11, 11, 11);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        questionsListWidget = new QListWidget(ParticipantWindow);
        questionsListWidget->setObjectName(QString::fromUtf8("questionsListWidget"));

        verticalLayout->addWidget(questionsListWidget);

        horizontalLayout_3 = new QHBoxLayout();
        horizontalLayout_3->setSpacing(6);
        horizontalLayout_3->setObjectName(QString::fromUtf8("horizontalLayout_3"));
        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setSpacing(6);
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        answerLineEdit = new QLineEdit(ParticipantWindow);
        answerLineEdit->setObjectName(QString::fromUtf8("answerLineEdit"));

        horizontalLayout->addWidget(answerLineEdit);


        horizontalLayout_3->addLayout(horizontalLayout);

        answerButton = new QPushButton(ParticipantWindow);
        answerButton->setObjectName(QString::fromUtf8("answerButton"));

        horizontalLayout_3->addWidget(answerButton);


        verticalLayout->addLayout(horizontalLayout_3);

        horizontalLayout_2 = new QHBoxLayout();
        horizontalLayout_2->setSpacing(6);
        horizontalLayout_2->setObjectName(QString::fromUtf8("horizontalLayout_2"));
        label = new QLabel(ParticipantWindow);
        label->setObjectName(QString::fromUtf8("label"));

        horizontalLayout_2->addWidget(label);

        scoreLineEdit = new QLineEdit(ParticipantWindow);
        scoreLineEdit->setObjectName(QString::fromUtf8("scoreLineEdit"));

        horizontalLayout_2->addWidget(scoreLineEdit);


        verticalLayout->addLayout(horizontalLayout_2);


        retranslateUi(ParticipantWindow);
        QObject::connect(answerButton, SIGNAL(clicked()), ParticipantWindow, SLOT(answerQuestion()));
        QObject::connect(questionsListWidget, SIGNAL(itemSelectionChanged()), ParticipantWindow, SLOT(setAnswerButton()));

        QMetaObject::connectSlotsByName(ParticipantWindow);
    } // setupUi

    void retranslateUi(QWidget *ParticipantWindow)
    {
        ParticipantWindow->setWindowTitle(QCoreApplication::translate("ParticipantWindow", "ParticipantWindow", nullptr));
        answerButton->setText(QCoreApplication::translate("ParticipantWindow", "Answer", nullptr));
        label->setText(QCoreApplication::translate("ParticipantWindow", "SCORE", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ParticipantWindow: public Ui_ParticipantWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_PARTICIPANTWINDOW_H
