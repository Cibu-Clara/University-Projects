/********************************************************************************
** Form generated from reading UI file 'UserGUI.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_USERGUI_H
#define UI_USERGUI_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QRadioButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_UserGUIClass
{
public:
    QVBoxLayout *verticalLayout;
    QLabel *titleWidget;
    QGridLayout *radioButtonsLayout;
    QRadioButton *HTMLbutton;
    QRadioButton *CSVbutton;
    QPushButton *openFileButton;
    QListWidget *movieListWdget;
    QListWidget *watchlistWidget;
    QFormLayout *movieDetailsLayout;
    QLabel *titleLabel;
    QLabel *genreLabel;
    QLineEdit *genreLineEdit;
    QLabel *yearLabel;
    QLineEdit *yearLineEdit;
    QLabel *likesLabel;
    QLineEdit *likesLineEdit;
    QLabel *linkLabel;
    QLineEdit *linkLineEdit;
    QLineEdit *titleLineEdit;
    QPushButton *nextButton;
    QPushButton *addButton;
    QPushButton *removeButton;
    QLineEdit *filterLineEdit;
    QPushButton *filterButton;
    QListWidget *filterMovieListWidget;

    void setupUi(QWidget *UserGUIClass)
    {
        if (UserGUIClass->objectName().isEmpty())
            UserGUIClass->setObjectName(QString::fromUtf8("UserGUIClass"));
        UserGUIClass->resize(533, 623);
        verticalLayout = new QVBoxLayout(UserGUIClass);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        titleWidget = new QLabel(UserGUIClass);
        titleWidget->setObjectName(QString::fromUtf8("titleWidget"));
        QFont font;
        font.setPointSize(14);
        font.setItalic(true);
        titleWidget->setFont(font);

        verticalLayout->addWidget(titleWidget);

        radioButtonsLayout = new QGridLayout();
        radioButtonsLayout->setObjectName(QString::fromUtf8("radioButtonsLayout"));
        HTMLbutton = new QRadioButton(UserGUIClass);
        HTMLbutton->setObjectName(QString::fromUtf8("HTMLbutton"));

        radioButtonsLayout->addWidget(HTMLbutton, 2, 0, 1, 1);

        CSVbutton = new QRadioButton(UserGUIClass);
        CSVbutton->setObjectName(QString::fromUtf8("CSVbutton"));

        radioButtonsLayout->addWidget(CSVbutton, 0, 0, 1, 1);

        openFileButton = new QPushButton(UserGUIClass);
        openFileButton->setObjectName(QString::fromUtf8("openFileButton"));

        radioButtonsLayout->addWidget(openFileButton, 0, 1, 1, 1);


        verticalLayout->addLayout(radioButtonsLayout);

        movieListWdget = new QListWidget(UserGUIClass);
        movieListWdget->setObjectName(QString::fromUtf8("movieListWdget"));

        verticalLayout->addWidget(movieListWdget);

        watchlistWidget = new QListWidget(UserGUIClass);
        watchlistWidget->setObjectName(QString::fromUtf8("watchlistWidget"));

        verticalLayout->addWidget(watchlistWidget);

        movieDetailsLayout = new QFormLayout();
        movieDetailsLayout->setObjectName(QString::fromUtf8("movieDetailsLayout"));
        titleLabel = new QLabel(UserGUIClass);
        titleLabel->setObjectName(QString::fromUtf8("titleLabel"));

        movieDetailsLayout->setWidget(0, QFormLayout::LabelRole, titleLabel);

        genreLabel = new QLabel(UserGUIClass);
        genreLabel->setObjectName(QString::fromUtf8("genreLabel"));

        movieDetailsLayout->setWidget(1, QFormLayout::LabelRole, genreLabel);

        genreLineEdit = new QLineEdit(UserGUIClass);
        genreLineEdit->setObjectName(QString::fromUtf8("genreLineEdit"));

        movieDetailsLayout->setWidget(1, QFormLayout::FieldRole, genreLineEdit);

        yearLabel = new QLabel(UserGUIClass);
        yearLabel->setObjectName(QString::fromUtf8("yearLabel"));

        movieDetailsLayout->setWidget(2, QFormLayout::LabelRole, yearLabel);

        yearLineEdit = new QLineEdit(UserGUIClass);
        yearLineEdit->setObjectName(QString::fromUtf8("yearLineEdit"));

        movieDetailsLayout->setWidget(2, QFormLayout::FieldRole, yearLineEdit);

        likesLabel = new QLabel(UserGUIClass);
        likesLabel->setObjectName(QString::fromUtf8("likesLabel"));

        movieDetailsLayout->setWidget(3, QFormLayout::LabelRole, likesLabel);

        likesLineEdit = new QLineEdit(UserGUIClass);
        likesLineEdit->setObjectName(QString::fromUtf8("likesLineEdit"));

        movieDetailsLayout->setWidget(3, QFormLayout::FieldRole, likesLineEdit);

        linkLabel = new QLabel(UserGUIClass);
        linkLabel->setObjectName(QString::fromUtf8("linkLabel"));

        movieDetailsLayout->setWidget(4, QFormLayout::LabelRole, linkLabel);

        linkLineEdit = new QLineEdit(UserGUIClass);
        linkLineEdit->setObjectName(QString::fromUtf8("linkLineEdit"));

        movieDetailsLayout->setWidget(4, QFormLayout::FieldRole, linkLineEdit);

        titleLineEdit = new QLineEdit(UserGUIClass);
        titleLineEdit->setObjectName(QString::fromUtf8("titleLineEdit"));

        movieDetailsLayout->setWidget(0, QFormLayout::FieldRole, titleLineEdit);


        verticalLayout->addLayout(movieDetailsLayout);

        nextButton = new QPushButton(UserGUIClass);
        nextButton->setObjectName(QString::fromUtf8("nextButton"));

        verticalLayout->addWidget(nextButton);

        addButton = new QPushButton(UserGUIClass);
        addButton->setObjectName(QString::fromUtf8("addButton"));

        verticalLayout->addWidget(addButton);

        removeButton = new QPushButton(UserGUIClass);
        removeButton->setObjectName(QString::fromUtf8("removeButton"));

        verticalLayout->addWidget(removeButton);

        filterLineEdit = new QLineEdit(UserGUIClass);
        filterLineEdit->setObjectName(QString::fromUtf8("filterLineEdit"));

        verticalLayout->addWidget(filterLineEdit);

        filterButton = new QPushButton(UserGUIClass);
        filterButton->setObjectName(QString::fromUtf8("filterButton"));

        verticalLayout->addWidget(filterButton);

        filterMovieListWidget = new QListWidget(UserGUIClass);
        filterMovieListWidget->setObjectName(QString::fromUtf8("filterMovieListWidget"));

        verticalLayout->addWidget(filterMovieListWidget);


        retranslateUi(UserGUIClass);
        QObject::connect(openFileButton, SIGNAL(clicked()), UserGUIClass, SLOT(fileHandler()));
        QObject::connect(nextButton, SIGNAL(clicked()), UserGUIClass, SLOT(nextMovie()));
        QObject::connect(addButton, SIGNAL(clicked()), UserGUIClass, SLOT(addWatchlistHandler()));
        QObject::connect(removeButton, SIGNAL(clicked()), UserGUIClass, SLOT(removeWatchlistHandler()));
        QObject::connect(filterButton, SIGNAL(clicked()), UserGUIClass, SLOT(filterMoviesHandler()));

        QMetaObject::connectSlotsByName(UserGUIClass);
    } // setupUi

    void retranslateUi(QWidget *UserGUIClass)
    {
        UserGUIClass->setWindowTitle(QCoreApplication::translate("UserGUIClass", "UserGUI", nullptr));
        titleWidget->setText(QCoreApplication::translate("UserGUIClass", "Select the type of file you want for saving your watchlist!", nullptr));
        HTMLbutton->setText(QCoreApplication::translate("UserGUIClass", "HTML", nullptr));
        CSVbutton->setText(QCoreApplication::translate("UserGUIClass", "CSV", nullptr));
        openFileButton->setText(QCoreApplication::translate("UserGUIClass", "Open file", nullptr));
        titleLabel->setText(QCoreApplication::translate("UserGUIClass", "Title", nullptr));
        genreLabel->setText(QCoreApplication::translate("UserGUIClass", "Genre", nullptr));
        yearLabel->setText(QCoreApplication::translate("UserGUIClass", "Year", nullptr));
        likesLabel->setText(QCoreApplication::translate("UserGUIClass", "Likes", nullptr));
        linkLabel->setText(QCoreApplication::translate("UserGUIClass", "Link", nullptr));
        nextButton->setText(QCoreApplication::translate("UserGUIClass", "Next movie", nullptr));
        addButton->setText(QCoreApplication::translate("UserGUIClass", "Add movie to watchlist", nullptr));
        removeButton->setText(QCoreApplication::translate("UserGUIClass", "Remove movie from watchlist", nullptr));
        filterLineEdit->setText(QString());
        filterButton->setText(QCoreApplication::translate("UserGUIClass", "Filter by genre", nullptr));
    } // retranslateUi

};

namespace Ui {
    class UserGUIClass: public Ui_UserGUIClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_USERGUI_H
