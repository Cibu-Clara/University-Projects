package com.example.exam

import com.example.exam.model.Property

object Constants {
    const val SERVER_IP = "192.168.1.131"
    const val SERVER_PORT = "2309"

    const val NAVIGATION_LIST = "list"
    const val NAVIGATION_CREATE = "create"
    const val NAVIGATION_EDIT = "edit/{id}"
    const val NAVIGATION_DETAILS = "details/{id}"
    const val NAVIGATION_ARGUMENT = "id"

    fun detailNavigation(id: Int) = "details/$id"
    fun editNavigation(id: Int) = "edit/$id"

    val ENTITY_PLACE_HOLDER = Property(
        id = -1,
        address = "",
        date = "",
        type = "",
        bedrooms = -1,
        bathrooms = -1,
        price = -1.0,
        area = -1,
        notes = ""
    )
}