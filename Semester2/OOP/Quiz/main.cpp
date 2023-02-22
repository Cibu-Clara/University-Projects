#include "Quiz.h"
#include "ParticipantWindow.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    Repository* repo = new Repository;
    Service* serv = new Service(*repo);

    std::vector<Participant> participants = serv->getParticipants();
    for (auto& participant : participants) {
        auto* newWindow = new ParticipantWindow(serv, participant);
        newWindow->setWindowTitle(QString::fromStdString(participant.getName()));
        serv->addObserver(newWindow);
        newWindow->show();
    }

    Quiz w{serv};
    serv->addObserver(&w);
    w.show();
    return a.exec();
}
