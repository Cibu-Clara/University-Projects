/********************************************************************************
** Form generated from reading UI file 'AdminGUI.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_ADMINGUI_H
#define UI_ADMINGUI_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_AdminGUIClass
{
public:
    QVBoxLayout *verticalLayout;
    QLabel *titleWidget;
    QListWidget *movieListWidget;
    QFormLayout *movieDetailsLayout;
    QLabel *titleLabel;
    QLineEdit *titleLineEdit;
    QLabel *genreLabel;
    QLineEdit *genreLineEdit;
    QLabel *yearLabel;
    QLineEdit *yearLineEdit;
    QLabel *likesLabel;
    QLineEdit *likesLineEdit;
    QLabel *linkLabel;
    QLineEdit *linkLineEdit;
    QGridLayout *buttonsLayout;
    QPushButton *addButton;
    QPushButton *deleteButton;
    QPushButton *updateButton;

    void setupUi(QWidget *AdminGUIClass)
    {
        if (AdminGUIClass->objectName().isEmpty())
            AdminGUIClass->setObjectName(QString::fromUtf8("AdminGUIClass"));
        AdminGUIClass->resize(325, 439);
        verticalLayout = new QVBoxLayout(AdminGUIClass);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        titleWidget = new QLabel(AdminGUIClass);
        titleWidget->setObjectName(QString::fromUtf8("titleWidget"));
        QFont font;
        font.setPointSize(14);
        font.setItalic(true);
        titleWidget->setFont(font);
        titleWidget->setAlignment(Qt::AlignCenter);

        verticalLayout->addWidget(titleWidget);

        movieListWidget = new QListWidget(AdminGUIClass);
        movieListWidget->setObjectName(QString::fromUtf8("movieListWidget"));

        verticalLayout->addWidget(movieListWidget);

        movieDetailsLayout = new QFormLayout();
        movieDetailsLayout->setObjectName(QString::fromUtf8("movieDetailsLayout"));
        titleLabel = new QLabel(AdminGUIClass);
        titleLabel->setObjectName(QString::fromUtf8("titleLabel"));

        movieDetailsLayout->setWidget(0, QFormLayout::LabelRole, titleLabel);

        titleLineEdit = new QLineEdit(AdminGUIClass);
        titleLineEdit->setObjectName(QString::fromUtf8("titleLineEdit"));

        movieDetailsLayout->setWidget(0, QFormLayout::FieldRole, titleLineEdit);

        genreLabel = new QLabel(AdminGUIClass);
        genreLabel->setObjectName(QString::fromUtf8("genreLabel"));

        movieDetailsLayout->setWidget(1, QFormLayout::LabelRole, genreLabel);

        genreLineEdit = new QLineEdit(AdminGUIClass);
        genreLineEdit->setObjectName(QString::fromUtf8("genreLineEdit"));

        movieDetailsLayout->setWidget(1, QFormLayout::FieldRole, genreLineEdit);

        yearLabel = new QLabel(AdminGUIClass);
        yearLabel->setObjectName(QString::fromUtf8("yearLabel"));

        movieDetailsLayout->setWidget(2, QFormLayout::LabelRole, yearLabel);

        yearLineEdit = new QLineEdit(AdminGUIClass);
        yearLineEdit->setObjectName(QString::fromUtf8("yearLineEdit"));

        movieDetailsLayout->setWidget(2, QFormLayout::FieldRole, yearLineEdit);

        likesLabel = new QLabel(AdminGUIClass);
        likesLabel->setObjectName(QString::fromUtf8("likesLabel"));

        movieDetailsLayout->setWidget(3, QFormLayout::LabelRole, likesLabel);

        likesLineEdit = new QLineEdit(AdminGUIClass);
        likesLineEdit->setObjectName(QString::fromUtf8("likesLineEdit"));

        movieDetailsLayout->setWidget(3, QFormLayout::FieldRole, likesLineEdit);

        linkLabel = new QLabel(AdminGUIClass);
        linkLabel->setObjectName(QString::fromUtf8("linkLabel"));

        movieDetailsLayout->setWidget(4, QFormLayout::LabelRole, linkLabel);

        linkLineEdit = new QLineEdit(AdminGUIClass);
        linkLineEdit->setObjectName(QString::fromUtf8("linkLineEdit"));

        movieDetailsLayout->setWidget(4, QFormLayout::FieldRole, linkLineEdit);


        verticalLayout->addLayout(movieDetailsLayout);

        buttonsLayout = new QGridLayout();
        buttonsLayout->setObjectName(QString::fromUtf8("buttonsLayout"));
        addButton = new QPushButton(AdminGUIClass);
        addButton->setObjectName(QString::fromUtf8("addButton"));

        buttonsLayout->addWidget(addButton, 0, 0, 1, 1);

        deleteButton = new QPushButton(AdminGUIClass);
        deleteButton->setObjectName(QString::fromUtf8("deleteButton"));

        buttonsLayout->addWidget(deleteButton, 0, 1, 1, 1);

        updateButton = new QPushButton(AdminGUIClass);
        updateButton->setObjectName(QString::fromUtf8("updateButton"));

        buttonsLayout->addWidget(updateButton, 1, 0, 1, 2);


        verticalLayout->addLayout(buttonsLayout);


        retranslateUi(AdminGUIClass);
        QObject::connect(addButton, SIGNAL(clicked()), AdminGUIClass, SLOT(addMovieHandler()));
        QObject::connect(updateButton, SIGNAL(clicked()), AdminGUIClass, SLOT(updateMovieHandler()));

        QMetaObject::connectSlotsByName(AdminGUIClass);
    } // setupUi

    void retranslateUi(QWidget *AdminGUIClass)
    {
        AdminGUIClass->setWindowTitle(QCoreApplication::translate("AdminGUIClass", "AdminGUI", nullptr));
        titleWidget->setText(QCoreApplication::translate("AdminGUIClass", "ADMIN MODE", nullptr));
        titleLabel->setText(QCoreApplication::translate("AdminGUIClass", "Title", nullptr));
        genreLabel->setText(QCoreApplication::translate("AdminGUIClass", "Genre", nullptr));
        yearLabel->setText(QCoreApplication::translate("AdminGUIClass", "Year", nullptr));
        likesLabel->setText(QCoreApplication::translate("AdminGUIClass", "Likes", nullptr));
        linkLabel->setText(QCoreApplication::translate("AdminGUIClass", "Link", nullptr));
        addButton->setText(QCoreApplication::translate("AdminGUIClass", "Add", nullptr));
        deleteButton->setText(QCoreApplication::translate("AdminGUIClass", "Delete", nullptr));
        updateButton->setText(QCoreApplication::translate("AdminGUIClass", "Update", nullptr));
    } // retranslateUi

};

namespace Ui {
    class AdminGUIClass: public Ui_AdminGUIClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ADMINGUI_H
