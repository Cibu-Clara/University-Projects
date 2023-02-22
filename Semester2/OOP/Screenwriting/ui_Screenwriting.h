/********************************************************************************
** Form generated from reading UI file 'Screenwriting.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_SCREENWRITING_H
#define UI_SCREENWRITING_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ScreenwritingClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *ScreenwritingClass)
    {
        if (ScreenwritingClass->objectName().isEmpty())
            ScreenwritingClass->setObjectName(QString::fromUtf8("ScreenwritingClass"));
        ScreenwritingClass->resize(600, 400);
        menuBar = new QMenuBar(ScreenwritingClass);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        ScreenwritingClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(ScreenwritingClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        ScreenwritingClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(ScreenwritingClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        ScreenwritingClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(ScreenwritingClass);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        ScreenwritingClass->setStatusBar(statusBar);

        retranslateUi(ScreenwritingClass);

        QMetaObject::connectSlotsByName(ScreenwritingClass);
    } // setupUi

    void retranslateUi(QMainWindow *ScreenwritingClass)
    {
        ScreenwritingClass->setWindowTitle(QCoreApplication::translate("ScreenwritingClass", "Screenwriting", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ScreenwritingClass: public Ui_ScreenwritingClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_SCREENWRITING_H
