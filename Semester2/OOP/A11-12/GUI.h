#pragma once
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QWidget>
#include "ui_GUI.h"
#include "ui_AdminGUI.h"
#include "ui_UserGUI.h"
#include "adminService.h"
#include "userService.h"

class GUI : public QMainWindow
{
    Q_OBJECT

public:
    GUI(AdminService* admin_serv, UserService* user_serv, QWidget* parent = Q_NULLPTR);
    ~GUI();

private:
    Ui::GUIClass ui;
    AdminService* admin_serv;
    UserService* user_serv;

    void showAdmin();
    void showUser();
    void connectSignalsAndSlots();
};

class AdminGUI : public QWidget
{
    Q_OBJECT

public:
    AdminGUI(AdminService* admin_serv, QWidget* parent = Q_NULLPTR);
    ~AdminGUI();

private:
    Ui::AdminGUIClass admin_ui;
    AdminService* admin_serv;

    void populateList();
    void addMovie(std::string title, std::string genre, size_t year, size_t likes, std::string link);
    int getSelectedIndex() const;
    void deleteMovieHandler();
    void deleteMovie(std::string title);
    void updateMovie(std::string t, std::string title, std::string genre, size_t year, size_t likes, std::string link);
    void connectSignalsAndSlots();

public slots:
    void addMovieHandler();
    void updateMovieHandler();
};

class UserGUI : public QWidget
{
    Q_OBJECT

public:
    UserGUI(UserService* user_serv, QWidget* parent = Q_NULLPTR);
    ~UserGUI();

private:
    Ui::UserGUIClass user_ui;
    UserService* user_serv;
    int index;

    void populateMovieList();
    void populateWatchlist();
    void updateLineEdit();
    int getSelectedIndexWatchlist() const;
    void addWatchlist(std::string title, std::string genre, size_t year, size_t likes, std::string link);
    void removeWatchlist(std::string title);
    void filterMovies(std::string genre);
    void populateFilterList();

public slots:
    void fileHandler();
    void nextMovie();
    void addWatchlistHandler();
    void removeWatchlistHandler();
    void filterMoviesHandler();
};