package com.example.petowners

import com.example.petowners.model.Pet

object Constants {

    const val SERVER_IP = "192.168.1.131"
    const val SERVER_PORT = "2309"

    const val TABLE_NAME = "pets"
    const val DATABASE_NAME = "db_petowners"

    const val NAVIGATION_PETS_LIST = "petsList"
    const val NAVIGATION_PET_DETAILS = "petDetails/{id}"
    const val NAVIGATION_ADD_PET = "addPet"
    const val NAVIGATION_ARGUMENT = "id"

    fun petDetailsNavigation(id: Int) = "petDetails/$id"

    val PET_DETAILS_PLACEHOLDER = Pet(
        id = -1,
        name = "",
        breed = "",
        age = -1,
        weight = -1,
        owner = "",
        location = "",
        description = ""
    )
}