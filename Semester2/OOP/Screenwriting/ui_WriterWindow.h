/********************************************************************************
** Form generated from reading UI file 'WriterWindow.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_WRITERWINDOW_H
#define UI_WRITERWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTableView>
#include <QtWidgets/QTextEdit>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_WriterWindow
{
public:
    QTableView *tableView;
    QWidget *layoutWidget;
    QHBoxLayout *horizontalLayout;
    QVBoxLayout *verticalLayout;
    QFormLayout *formLayout;
    QLabel *descriptionLabel;
    QLineEdit *descriptionLineEdit;
    QLabel *actLabel;
    QLineEdit *actLineEdit;
    QPushButton *addButton;
    QPushButton *acceptButton;
    QPushButton *savePlotButton;
    QVBoxLayout *verticalLayout_2;
    QPushButton *developButton;
    QTextEdit *textEdit;
    QPushButton *saveButton;

    void setupUi(QWidget *WriterWindow)
    {
        if (WriterWindow->objectName().isEmpty())
            WriterWindow->setObjectName(QString::fromUtf8("WriterWindow"));
        WriterWindow->resize(400, 358);
        tableView = new QTableView(WriterWindow);
        tableView->setObjectName(QString::fromUtf8("tableView"));
        tableView->setGeometry(QRect(10, 10, 381, 191));
        layoutWidget = new QWidget(WriterWindow);
        layoutWidget->setObjectName(QString::fromUtf8("layoutWidget"));
        layoutWidget->setGeometry(QRect(10, 210, 381, 144));
        horizontalLayout = new QHBoxLayout(layoutWidget);
        horizontalLayout->setSpacing(6);
        horizontalLayout->setContentsMargins(11, 11, 11, 11);
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        verticalLayout = new QVBoxLayout();
        verticalLayout->setSpacing(6);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        formLayout = new QFormLayout();
        formLayout->setSpacing(6);
        formLayout->setObjectName(QString::fromUtf8("formLayout"));
        descriptionLabel = new QLabel(layoutWidget);
        descriptionLabel->setObjectName(QString::fromUtf8("descriptionLabel"));

        formLayout->setWidget(0, QFormLayout::LabelRole, descriptionLabel);

        descriptionLineEdit = new QLineEdit(layoutWidget);
        descriptionLineEdit->setObjectName(QString::fromUtf8("descriptionLineEdit"));

        formLayout->setWidget(0, QFormLayout::FieldRole, descriptionLineEdit);

        actLabel = new QLabel(layoutWidget);
        actLabel->setObjectName(QString::fromUtf8("actLabel"));

        formLayout->setWidget(1, QFormLayout::LabelRole, actLabel);

        actLineEdit = new QLineEdit(layoutWidget);
        actLineEdit->setObjectName(QString::fromUtf8("actLineEdit"));

        formLayout->setWidget(1, QFormLayout::FieldRole, actLineEdit);


        verticalLayout->addLayout(formLayout);

        addButton = new QPushButton(layoutWidget);
        addButton->setObjectName(QString::fromUtf8("addButton"));

        verticalLayout->addWidget(addButton);

        acceptButton = new QPushButton(layoutWidget);
        acceptButton->setObjectName(QString::fromUtf8("acceptButton"));

        verticalLayout->addWidget(acceptButton);

        savePlotButton = new QPushButton(layoutWidget);
        savePlotButton->setObjectName(QString::fromUtf8("savePlotButton"));

        verticalLayout->addWidget(savePlotButton);


        horizontalLayout->addLayout(verticalLayout);

        verticalLayout_2 = new QVBoxLayout();
        verticalLayout_2->setSpacing(6);
        verticalLayout_2->setObjectName(QString::fromUtf8("verticalLayout_2"));
        developButton = new QPushButton(layoutWidget);
        developButton->setObjectName(QString::fromUtf8("developButton"));

        verticalLayout_2->addWidget(developButton);

        textEdit = new QTextEdit(layoutWidget);
        textEdit->setObjectName(QString::fromUtf8("textEdit"));

        verticalLayout_2->addWidget(textEdit);

        saveButton = new QPushButton(layoutWidget);
        saveButton->setObjectName(QString::fromUtf8("saveButton"));

        verticalLayout_2->addWidget(saveButton);


        horizontalLayout->addLayout(verticalLayout_2);


        retranslateUi(WriterWindow);
        QObject::connect(addButton, SIGNAL(clicked()), WriterWindow, SLOT(addHandler()));
        QObject::connect(developButton, SIGNAL(clicked()), WriterWindow, SLOT(develop()));
        QObject::connect(saveButton, SIGNAL(clicked()), WriterWindow, SLOT(saveHandler()));

        QMetaObject::connectSlotsByName(WriterWindow);
    } // setupUi

    void retranslateUi(QWidget *WriterWindow)
    {
        WriterWindow->setWindowTitle(QCoreApplication::translate("WriterWindow", "WriterWindow", nullptr));
        descriptionLabel->setText(QCoreApplication::translate("WriterWindow", "description", nullptr));
        actLabel->setText(QCoreApplication::translate("WriterWindow", "act", nullptr));
        addButton->setText(QCoreApplication::translate("WriterWindow", "Add", nullptr));
        acceptButton->setText(QCoreApplication::translate("WriterWindow", "Accept", nullptr));
        savePlotButton->setText(QCoreApplication::translate("WriterWindow", "Save plot", nullptr));
        developButton->setText(QCoreApplication::translate("WriterWindow", "Develop", nullptr));
        saveButton->setText(QCoreApplication::translate("WriterWindow", "Save", nullptr));
    } // retranslateUi

};

namespace Ui {
    class WriterWindow: public Ui_WriterWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_WRITERWINDOW_H
