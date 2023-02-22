/********************************************************************************
** Form generated from reading UI file 'Test.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_TEST_H
#define UI_TEST_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_TestClass
{
public:
    QWidget *centralWidget;
    QVBoxLayout *verticalLayout;
    QLabel *title;
    QListWidget *weatherListWidget;
    QLineEdit *filterLineEdit;
    QPushButton *filterButton;
    QLineEdit *descriptionLineEdit;
    QLineEdit *timeLineEdit;
    QPushButton *computeButton;
    QLineEdit *totalLineEdit;

    void setupUi(QMainWindow *TestClass)
    {
        if (TestClass->objectName().isEmpty())
            TestClass->setObjectName(QString::fromUtf8("TestClass"));
        TestClass->resize(289, 400);
        centralWidget = new QWidget(TestClass);
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

        weatherListWidget = new QListWidget(centralWidget);
        weatherListWidget->setObjectName(QString::fromUtf8("weatherListWidget"));

        verticalLayout->addWidget(weatherListWidget);

        filterLineEdit = new QLineEdit(centralWidget);
        filterLineEdit->setObjectName(QString::fromUtf8("filterLineEdit"));

        verticalLayout->addWidget(filterLineEdit);

        filterButton = new QPushButton(centralWidget);
        filterButton->setObjectName(QString::fromUtf8("filterButton"));

        verticalLayout->addWidget(filterButton);

        descriptionLineEdit = new QLineEdit(centralWidget);
        descriptionLineEdit->setObjectName(QString::fromUtf8("descriptionLineEdit"));

        verticalLayout->addWidget(descriptionLineEdit);

        timeLineEdit = new QLineEdit(centralWidget);
        timeLineEdit->setObjectName(QString::fromUtf8("timeLineEdit"));

        verticalLayout->addWidget(timeLineEdit);

        computeButton = new QPushButton(centralWidget);
        computeButton->setObjectName(QString::fromUtf8("computeButton"));

        verticalLayout->addWidget(computeButton);

        totalLineEdit = new QLineEdit(centralWidget);
        totalLineEdit->setObjectName(QString::fromUtf8("totalLineEdit"));

        verticalLayout->addWidget(totalLineEdit);

        TestClass->setCentralWidget(centralWidget);

        retranslateUi(TestClass);
        QObject::connect(filterButton, SIGNAL(clicked()), TestClass, SLOT(filterHandler()));
        QObject::connect(computeButton, SIGNAL(clicked()), TestClass, SLOT(totalHandler()));

        QMetaObject::connectSlotsByName(TestClass);
    } // setupUi

    void retranslateUi(QMainWindow *TestClass)
    {
        TestClass->setWindowTitle(QCoreApplication::translate("TestClass", "Test", nullptr));
        title->setText(QCoreApplication::translate("TestClass", "Weather", nullptr));
        filterButton->setText(QCoreApplication::translate("TestClass", "Filter", nullptr));
        computeButton->setText(QCoreApplication::translate("TestClass", "Compute total", nullptr));
    } // retranslateUi

};

namespace Ui {
    class TestClass: public Ui_TestClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_TEST_H
