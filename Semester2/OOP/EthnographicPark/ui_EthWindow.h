/********************************************************************************
** Form generated from reading UI file 'EthWindow.ui'
**
** Created by: Qt User Interface Compiler version 6.3.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_ETHWINDOW_H
#define UI_ETHWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_EthWindow
{
public:

    void setupUi(QWidget *EthWindow)
    {
        if (EthWindow->objectName().isEmpty())
            EthWindow->setObjectName(QString::fromUtf8("EthWindow"));
        EthWindow->resize(400, 300);

        retranslateUi(EthWindow);

        QMetaObject::connectSlotsByName(EthWindow);
    } // setupUi

    void retranslateUi(QWidget *EthWindow)
    {
        EthWindow->setWindowTitle(QCoreApplication::translate("EthWindow", "EthWindow", nullptr));
    } // retranslateUi

};

namespace Ui {
    class EthWindow: public Ui_EthWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ETHWINDOW_H
