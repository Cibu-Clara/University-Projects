#include "WriterWindow.h"
#include <QtWidgets/QApplication>
#include "Service.h"
#include <fstream>
#include "IdeasModel.h"

int main(int argc, char* argv[])
{
    QApplication a(argc, argv);

    std::string writersFile = "writers.txt";
    std::string ideasFile = "ideas.txt";
    std::ifstream fin("writers.txt");

    Writer w;
    Repository<Writer>* writers = new Repository<Writer>{ writersFile };
    Repository<Idea>* ideas = new Repository<Idea>{ ideasFile };
    Service* service = new Service{ writers, ideas };
    IdeaModel* model = new IdeaModel{ ideas };

    while (fin >> w)
    {
        WriterWindow* window = new WriterWindow(model, ideas, w);
        window->setWindowTitle(QString::fromStdString(w.getName()));
        window->show();
    }

    return a.exec();
}