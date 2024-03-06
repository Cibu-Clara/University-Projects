package com.example.exam

import com.example.exam.model.Property

object Constants {

    const val SERVER_IP = "172.30.113.46"
    const val SERVER_PORT = "2407"

    const val TABLE_NAME = "properties"
    const val DATABASE_NAME = "db_realestate"

    const val NAVIGATION_PROPERTIES_LIST = "propertiesList"
    const val NAVIGATION_CLIENT_SECTION = "clientSection"
    const val NAVIGATION_ADMIN_SECTION = "adminSection"
    const val NAVIGATION_CREATE_PROPERTY = "addProperty"
    const val NAVIGATION_PROPERTY_DETAILS = "propertyDetails/{id}"
    const val NAVIGATION_ARGUMENT = "id"

    fun propertyDetailsNavigation(id: Int) = "propertyDetails/$id"

    val PROPERTY_PLACE_HOLDER = Property(
        id = -1,
        name = "",
        date = "",
        details = "",
        status = "",
        viewers = -1,
        type = ""
    )
}