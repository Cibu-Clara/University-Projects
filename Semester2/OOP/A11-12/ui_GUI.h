/********************************************************************************
** Form generated from reading UI file 'GUI.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_GUI_H
#define UI_GUI_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_GUIClass
{
public:
    QWidget *centralWidget;
    QVBoxLayout *verticalLayout;
    QLabel *titleWidget;
    QPushButton *adminButton;
    QPushButton *userButton;

    void setupUi(QMainWindow *GUIClass)
    {
        if (GUIClass->objectName().isEmpty())
            GUIClass->setObjectName(QString::fromUtf8("GUIClass"));
        GUIClass->resize(277, 104);
        QFont font;
        font.setPointSize(9);
        font.setKerning(true);
        font.setStyleStrategy(QFont::PreferDefault);
        GUIClass->setFont(font);
        GUIClass->setTabletTracking(false);
        GUIClass->setLayoutDirection(Qt::LeftToRight);
        GUIClass->setAutoFillBackground(false);
        GUIClass->setIconSize(QSize(24, 24));
        GUIClass->setToolButtonStyle(Qt::ToolButtonIconOnly);
        GUIClass->setAnimated(true);
        GUIClass->setDockNestingEnabled(false);
        GUIClass->setUnifiedTitleAndToolBarOnMac(false);
        centralWidget = new QWidget(GUIClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        verticalLayout = new QVBoxLayout(centralWidget);
        verticalLayout->setSpacing(6);
        verticalLayout->setContentsMargins(11, 11, 11, 11);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        titleWidget = new QLabel(centralWidget);
        titleWidget->setObjectName(QString::fromUtf8("titleWidget"));
        QFont font1;
        font1.setPointSize(14);
        font1.setItalic(true);
        font1.setKerning(true);
        font1.setStyleStrategy(QFont::PreferDefault);
        titleWidget->setFont(font1);
        titleWidget->setAlignment(Qt::AlignCenter);

        verticalLayout->addWidget(titleWidget);

        adminButton = new QPushButton(centralWidget);
        adminButton->setObjectName(QString::fromUtf8("adminButton"));

        verticalLayout->addWidget(adminButton);

        userButton = new QPushButton(centralWidget);
        userButton->setObjectName(QString::fromUtf8("userButton"));
        userButton->setEnabled(true);

        verticalLayout->addWidget(userButton);

        GUIClass->setCentralWidget(centralWidget);

        retranslateUi(GUIClass);

        QMetaObject::connectSlotsByName(GUIClass);
    } // setupUi

    void retranslateUi(QMainWindow *GUIClass)
    {
        GUIClass->setWindowTitle(QCoreApplication::translate("GUIClass", "GUI", nullptr));
#if QT_CONFIG(tooltip)
        GUIClass->setToolTip(QString());
#endif // QT_CONFIG(tooltip)
        titleWidget->setText(QCoreApplication::translate("GUIClass", "Welcome to movie department!", nullptr));
        adminButton->setText(QCoreApplication::translate("GUIClass", "Admin mode", nullptr));
        userButton->setText(QCoreApplication::translate("GUIClass", "User mode", nullptr));
    } // retranslateUi

};

namespace Ui {
    class GUIClass: public Ui_GUIClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_GUI_H
