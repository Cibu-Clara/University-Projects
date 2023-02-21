#include "GUI.h"
#include <QMessageBox>
#include "exception.h"

GUI::GUI(AdminService* admin_serv, UserService* user_serv, QWidget* parent)
    : QMainWindow(parent), admin_serv(admin_serv), user_serv(user_serv)
{
    ui.setupUi(this);
    this->connectSignalsAndSlots();
}

GUI::~GUI()
{
}

void GUI::showAdmin()
{
    QWidget* admin = new AdminGUI(this->admin_serv);
    admin->show();
}

void GUI::showUser()
{
    QWidget* admin = new UserGUI(this->user_serv);
    admin->show();
}

void GUI::connectSignalsAndSlots()
{
    QObject::connect(this->ui.adminButton, &QPushButton::clicked, this, &GUI::showAdmin);
    QObject::connect(this->ui.userButton, &QPushButton::clicked, this, &GUI::showUser);
}

AdminGUI::AdminGUI(AdminService* admin_serv, QWidget* parent)
    : QWidget(parent), admin_serv(admin_serv)
{
    admin_ui.setupUi(this);
    this->connectSignalsAndSlots();
    this->populateList();
}

AdminGUI::~AdminGUI()
{
}

void AdminGUI::populateList()
{
    this->admin_ui.movieListWidget->clear();
    std::vector<Movie> allMovies = this->admin_serv->GetAll();
    for (auto& m : allMovies)
    {
        QString stringItem = QString::fromStdString(
            m.GetTitle() + "   " +
            m.GetGenre() + "   " +
            std::to_string(m.GetYear()) + "   " +
            std::to_string(m.GetLikes()) + "   " +
            m.GetTrailer());

        this->admin_ui.movieListWidget->addItem(stringItem);
    }
}

void AdminGUI::addMovieHandler()
{
    int year = atoi(this->admin_ui.yearLineEdit->text().toStdString().c_str());
    int likes = atoi(this->admin_ui.likesLineEdit->text().toStdString().c_str());
    std::string title = this->admin_ui.titleLineEdit->text().toStdString();
    std::string genre = this->admin_ui.genreLineEdit->text().toStdString();
    std::string link = this->admin_ui.linkLineEdit->text().toStdString();

    this->admin_ui.titleLineEdit->clear();
    this->admin_ui.genreLineEdit->clear();
    this->admin_ui.yearLineEdit->clear();
    this->admin_ui.likesLineEdit->clear();
    this->admin_ui.linkLineEdit->clear();

    addMovie(title, genre, year, likes, link);
}

void AdminGUI::addMovie(std::string title, std::string genre, size_t year, size_t likes, std::string link)
{
    std::string text;
    try
    {
        this->admin_serv->AddMovie(title, genre, year, likes, link);
        this->populateList();
        text = "Movie added succesfully!";
    }
    catch (ValidatorException ve)
    {
        text = ve.getMessage();
    }
    catch (RepoException re)
    {
        text = re.getMessage();
    }

    QMessageBox msgBox;
    msgBox.setText(text.c_str());
    msgBox.exec();
}

int AdminGUI::getSelectedIndex() const
{
    if (this->admin_ui.movieListWidget->count() == 0)
    {
        return -1;
    }

    QModelIndexList index = this->admin_ui.movieListWidget->selectionModel()->selectedRows();

    if (index.size() == 0)
    {
        this->admin_ui.titleLineEdit->clear();
        this->admin_ui.genreLineEdit->clear();
        this->admin_ui.yearLineEdit->clear();
        this->admin_ui.likesLineEdit->clear();
        this->admin_ui.linkLineEdit->clear();
        return -1;
    }

    return index.at(0).row();
}

void AdminGUI::deleteMovieHandler()
{
    int index = this->getSelectedIndex();
    if (index != -1)
    {
        Movie m = this->admin_serv->GetAll()[index];
        std::string title = m.GetTitle();
        deleteMovie(title);
    }

    else {
        QMessageBox mess;
        mess.setText("Please select the movie you want to remove!");
        mess.exec();
    }
}

void AdminGUI::deleteMovie(std::string title)
{
    this->admin_serv->RemoveMovie(title);
    this->populateList();
    std::string text = "Movie removed succesfully!";
  
    QMessageBox msgBox;
    msgBox.setText(text.c_str());
    msgBox.exec();
}

void AdminGUI::updateMovieHandler()
{
    int index = this->getSelectedIndex();
    if (index != -1)
    {
        Movie m = this->admin_serv->GetAll()[index];
        std::string t = m.GetTitle();
        std::string title = this->admin_ui.titleLineEdit->text().toStdString();
        std::string genre = this->admin_ui.genreLineEdit->text().toStdString();
        int year = atoi(this->admin_ui.yearLineEdit->text().toStdString().c_str());
        int likes = atoi(this->admin_ui.likesLineEdit->text().toStdString().c_str());
        std::string trailer = this->admin_ui.linkLineEdit->text().toStdString();

        this->admin_ui.titleLineEdit->clear();
        this->admin_ui.genreLineEdit->clear();
        this->admin_ui.yearLineEdit->clear();
        this->admin_ui.likesLineEdit->clear();
        this->admin_ui.linkLineEdit->clear();

        updateMovie(t, title, genre, year, likes, trailer);
    }
    else {
        QMessageBox mess;
        mess.setText("Please select the movie you want to update!");
        mess.exec();
    }
}

void AdminGUI::updateMovie(std::string t, std::string title, std::string genre, size_t year, size_t likes, std::string link)
{
    std::string text; 

    try
    {   
        int ok = 0;
        if (!title.empty()) this->admin_serv->UpdateMovieTitle(t, title), ok = 1;
        if (!genre.empty()) this->admin_serv->UpdateMovieGenre(t, genre), ok = 1;
        if (year) this->admin_serv->UpdateMovieYear(t, year), ok = 1;
        if (likes) this->admin_serv->UpdateMovieLikes(t, likes), ok = 1;
        if (!link.empty()) this->admin_serv->UpdateMovieTrailer(t, link), ok = 1;
        this->populateList();
        if (ok == 1)
            text = "Movie updated successfully!";
        else
            throw ValidatorException("Please complete at least one field!");
    }
    catch (ValidatorException ve)
    {
        text = ve.getMessage();
    }

    QMessageBox msgBox;
    msgBox.setText(text.c_str());
    msgBox.exec();
}

void AdminGUI::connectSignalsAndSlots()
{
    QObject::connect(this->admin_ui.deleteButton, &QPushButton::clicked, this, &AdminGUI::deleteMovieHandler);
}

UserGUI::UserGUI(UserService* user_serv, QWidget* parent)
    : QWidget(parent), user_serv(user_serv)
{
    this->index = -1;
    user_ui.setupUi(this);
    this->populateMovieList();
    this->nextMovie();
}

UserGUI::~UserGUI()
{
}

void UserGUI::populateMovieList()
{
    this->user_ui.movieListWdget->clear();
    std::vector<Movie> allMovies = this->user_serv->GetMovies();
    for (auto& m : allMovies)
        this->user_ui.movieListWdget->addItem(QString::fromStdString(m.GetTitle()));
}

void UserGUI::populateWatchlist()
{
    this->user_ui.watchlistWidget->clear();
    std::vector<Movie> allMovies = this->user_serv->GetMovieList();
    allMovies.erase(allMovies.begin());
    for (auto& m : allMovies)
    {
        QString stringItem = QString::fromStdString(
            m.GetTitle() + "   " +
            m.GetGenre() + "   " +
            std::to_string(m.GetYear()) + "   " +
            std::to_string(m.GetLikes()) + "   " +
            m.GetTrailer());

        this->user_ui.watchlistWidget->addItem(stringItem);
    }
}

void UserGUI::nextMovie()
{
    this->index++;
    this->index %= this->user_serv->GetMovies().size();
    this->updateLineEdit();
}

void UserGUI::populateFilterList()
{
    this->user_ui.filterMovieListWidget->clear();
    std::vector<Movie> allMovies = this->user_serv->getCurrentList();
    for (auto& m : allMovies)
    {
        QString stringItem = QString::fromStdString(
            m.GetTitle() + "   " +
            m.GetGenre() + "   " +
            std::to_string(m.GetYear()) + "   " +
            std::to_string(m.GetLikes()) + "   " +
            m.GetTrailer());

        this->user_ui.filterMovieListWidget->addItem(stringItem);
    }
}

void UserGUI::updateLineEdit()
{
    Movie m = this->user_serv->GetMovies()[index];

    this->user_ui.titleLineEdit->setText(QString::fromStdString(std::string(m.GetTitle())));
    this->user_ui.genreLineEdit->setText(QString::fromStdString(std::string(m.GetGenre())));
    this->user_ui.yearLineEdit->setText(QString::fromStdString(std::to_string(m.GetYear())));
    this->user_ui.likesLineEdit->setText(QString::fromStdString(std::to_string(m.GetLikes())));
    this->user_ui.linkLineEdit->setText(QString::fromStdString(std::string(m.GetTrailer())));
}

void UserGUI::addWatchlistHandler()
{
    int year = atoi(this->user_ui.yearLineEdit->text().toStdString().c_str());
    int likes = atoi(this->user_ui.likesLineEdit->text().toStdString().c_str());
    std::string title = this->user_ui.titleLineEdit->text().toStdString();
    std::string genre = this->user_ui.genreLineEdit->text().toStdString();
    std::string link = this->user_ui.linkLineEdit->text().toStdString();
   
    addWatchlist(title, genre, year, likes, link);
}

void UserGUI::addWatchlist(std::string title, std::string genre, size_t year, size_t likes, std::string link)
{
    std::string text;
    try 
    {
        this->user_serv->AddToWatchList(Movie(title, genre, year, likes, link));
        this->populateWatchlist();
        text = "Movie added succesfully!";
    }
    catch (RepoException re)
    {
        text = re.getMessage();
    }
    nextMovie();
    QMessageBox msgBox;
    msgBox.setText(text.c_str());
    msgBox.exec();
}

int UserGUI::getSelectedIndexWatchlist() const
{
    if (this->user_ui.watchlistWidget->count() == 0)
    {
        return -1;
    }

    QModelIndexList index = this->user_ui.watchlistWidget->selectionModel()->selectedRows();

    if (index.size() == 0)
    {
        this->user_ui.titleLineEdit->clear();
        this->user_ui.genreLineEdit->clear();
        this->user_ui.yearLineEdit->clear();
        this->user_ui.likesLineEdit->clear();
        this->user_ui.linkLineEdit->clear();
        return -1;
    }

    return index.at(0).row();
}

void UserGUI::removeWatchlistHandler()
{
    int index = this->getSelectedIndexWatchlist();
    if (index != -1)
    {
        Movie m = this->user_serv->GetMovieList()[index + 1];
        std::string title = m.GetTitle();
        removeWatchlist(title);
    }

    else {
        QMessageBox mess;
        mess.setText("Please select the movie you want to remove!");
        mess.exec();
    }

}

void UserGUI::filterMoviesHandler()
{
    std::string genre = this->user_ui.filterLineEdit->text().toStdString();
    this->user_ui.filterLineEdit->clear();
    filterMovies(genre);
}

void UserGUI::filterMovies(std::string genre)
{
    std::string text = "Please enter genre!";

    if (genre.size())
    {
        this->user_serv->ReinitializeMovieList();
        this->user_serv->FilterByGenre(genre);
        populateFilterList();
    }
    else {
        QMessageBox msg;
        msg.setText(text.c_str());
        msg.exec();
    }
}

void UserGUI::removeWatchlist(std::string title)
{
    this->user_serv->DeleteMovieWatchList(title, 200);
    this->populateWatchlist();
    std::string text = "Movie removed succesfully!";

    QMessageBox msgBox;
    msgBox.setText(text.c_str());
    msgBox.exec();
}

void UserGUI::fileHandler()
{
    if (user_ui.CSVbutton->isChecked())
    {
        this->user_serv->SetWriteMode("CSV");
    }
    else if (user_ui.HTMLbutton->isChecked())
    {
        this->user_serv->SetWriteMode("HTML");
    }
    if (this->user_serv->GetWriteMode() == "CSV")
    {
        this->user_serv->WriteData();

        std::string command = "notepad " + this->user_serv->GetFileName();

        system("C: Users\\Clara\\Documents\\Clara\\UBB\\semester II\\OOP\\examples\\GUI");
        system(command.c_str());
    }
    else
    {
        this->user_serv->WriteData();

        system("C: Users\\Clara\\Documents\\Clara\\UBB\\semester II\\OOP\\examples\\GUI");
        system(this->user_serv->GetFileName().c_str());
    }
}