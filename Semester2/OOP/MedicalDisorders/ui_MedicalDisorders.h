/********************************************************************************
** Form generated from reading UI file 'MedicalDisorders.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MEDICALDISORDERS_H
#define UI_MEDICALDISORDERS_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MedicalDisordersClass
{
public:
    QWidget *centralWidget;
    QVBoxLayout *verticalLayout;
    QLabel *title;
    QListWidget *disordersListWidget;
    QLineEdit *searchBar;

    void setupUi(QMainWindow *MedicalDisordersClass)
    {
        if (MedicalDisordersClass->objectName().isEmpty())
            MedicalDisordersClass->setObjectName(QString::fromUtf8("MedicalDisordersClass"));
        MedicalDisordersClass->resize(248, 500);
        centralWidget = new QWidget(MedicalDisordersClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        verticalLayout = new QVBoxLayout(centralWidget);
        verticalLayout->setSpacing(6);
        verticalLayout->setContentsMargins(11, 11, 11, 11);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        title = new QLabel(centralWidget);
        title->setObjectName(QString::fromUtf8("title"));
        QFont font;
        font.setPointSize(14);
        font.setItalic(true);
        title->setFont(font);
        title->setAlignment(Qt::AlignCenter);

        verticalLayout->addWidget(title);

        disordersListWidget = new QListWidget(centralWidget);
        disordersListWidget->setObjectName(QString::fromUtf8("disordersListWidget"));

        verticalLayout->addWidget(disordersListWidget);

        searchBar = new QLineEdit(centralWidget);
        searchBar->setObjectName(QString::fromUtf8("searchBar"));

        verticalLayout->addWidget(searchBar);

        MedicalDisordersClass->setCentralWidget(centralWidget);

        retranslateUi(MedicalDisordersClass);
        QObject::connect(searchBar, SIGNAL(textChanged(QString)), MedicalDisordersClass, SLOT(updateListHandler()));

        QMetaObject::connectSlotsByName(MedicalDisordersClass);
    } // setupUi

    void retranslateUi(QMainWindow *MedicalDisordersClass)
    {
        MedicalDisordersClass->setWindowTitle(QCoreApplication::translate("MedicalDisordersClass", "MedicalDisorders", nullptr));
        title->setText(QCoreApplication::translate("MedicalDisordersClass", "Medical Disorders", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MedicalDisordersClass: public Ui_MedicalDisordersClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MEDICALDISORDERS_H
