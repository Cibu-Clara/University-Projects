package com.example.financetracker

object Constants {

    const val SERVER_IP = "192.168.1.131"
    const val SERVER_PORT = "2307"

    const val TABLE_NAME = "transactions"
    const val DATABASE_NAME = "db_financetracker"

    const val NAVIGATION_DATES_LIST = "daysList"
    const val NAVIGATION_DATE_DETAILS = "details/{date}"
    const val NAVIGATION_CREATE_TRANSACTION = "createTransaction"
    const val NAVIGATION_TOP_SECTION = "topSection"
    const val NAVIGATION_PROGRESS_SECTION = "progressSection"
    const val NAVIGATION_ARGUMENT = "date"

    fun dayDetailsNavigation(date: String) = "details/$date"
}