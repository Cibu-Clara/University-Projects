/********************************************************************************
** Form generated from reading UI file 'TaskManager.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_TASKMANAGER_H
#define UI_TASKMANAGER_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_TaskManagerClass
{
public:
    QWidget *centralWidget;
    QVBoxLayout *verticalLayout;
    QLabel *title;
    QListWidget *tasksListWidget;
    QLineEdit *filterLineEdit;
    QPushButton *filterButton;
    QLineEdit *priorityLineEdit;
    QHBoxLayout *horizontalLayout;
    QPushButton *durationButton;
    QLineEdit *durationLineEdit;
    QToolBar *mainToolBar;

    void setupUi(QMainWindow *TaskManagerClass)
    {
        if (TaskManagerClass->objectName().isEmpty())
            TaskManagerClass->setObjectName(QString::fromUtf8("TaskManagerClass"));
        TaskManagerClass->resize(272, 367);
        centralWidget = new QWidget(TaskManagerClass);
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

        tasksListWidget = new QListWidget(centralWidget);
        tasksListWidget->setObjectName(QString::fromUtf8("tasksListWidget"));

        verticalLayout->addWidget(tasksListWidget);

        filterLineEdit = new QLineEdit(centralWidget);
        filterLineEdit->setObjectName(QString::fromUtf8("filterLineEdit"));

        verticalLayout->addWidget(filterLineEdit);

        filterButton = new QPushButton(centralWidget);
        filterButton->setObjectName(QString::fromUtf8("filterButton"));

        verticalLayout->addWidget(filterButton);

        priorityLineEdit = new QLineEdit(centralWidget);
        priorityLineEdit->setObjectName(QString::fromUtf8("priorityLineEdit"));

        verticalLayout->addWidget(priorityLineEdit);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setSpacing(6);
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));
        durationButton = new QPushButton(centralWidget);
        durationButton->setObjectName(QString::fromUtf8("durationButton"));

        horizontalLayout->addWidget(durationButton);

        durationLineEdit = new QLineEdit(centralWidget);
        durationLineEdit->setObjectName(QString::fromUtf8("durationLineEdit"));

        horizontalLayout->addWidget(durationLineEdit);


        verticalLayout->addLayout(horizontalLayout);

        TaskManagerClass->setCentralWidget(centralWidget);
        mainToolBar = new QToolBar(TaskManagerClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        TaskManagerClass->addToolBar(Qt::TopToolBarArea, mainToolBar);

        retranslateUi(TaskManagerClass);
        QObject::connect(filterButton, SIGNAL(clicked()), TaskManagerClass, SLOT(filterHandler()));
        QObject::connect(durationButton, SIGNAL(clicked()), TaskManagerClass, SLOT(totalDurationHandler()));

        QMetaObject::connectSlotsByName(TaskManagerClass);
    } // setupUi

    void retranslateUi(QMainWindow *TaskManagerClass)
    {
        TaskManagerClass->setWindowTitle(QCoreApplication::translate("TaskManagerClass", "TaskManager", nullptr));
        title->setText(QCoreApplication::translate("TaskManagerClass", "Task Manager", nullptr));
        filterButton->setText(QCoreApplication::translate("TaskManagerClass", "Show by priority", nullptr));
        durationButton->setText(QCoreApplication::translate("TaskManagerClass", "      Show duration      ", nullptr));
    } // retranslateUi

};

namespace Ui {
    class TaskManagerClass: public Ui_TaskManagerClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_TASKMANAGER_H
