#pragma once
#include "Service.h"

class UI {
private:
    Service service;
public:
    UI(Service service);
    UI();
    ~UI();
    void print_menu();
    void run_command();
    void add_a_new_building();
    void show_all_buildings();
    void must_be_restored();
    void initial_list();
    void save_file();
};