package com.nikhil.harrypotterworld.data.model

data class CharacterModel(
    val actor: String,
    val alive: Boolean,
    val ancestry: String,
    val dateOfBirth: String? = null,
    val eyeColour: String? = null,
    val gender: String,
    val hairColour: String? = null,
    val hogwartsStaff: Boolean? = null,
    val hogwartsStudent: Boolean? = null,
    val house: String? = null,
    val id: String? = null,
    val image: String? = null,
    val name: String,
    val patronus: String? = null,
    val species: String? = null,
    val wizard: Boolean? = null,
    val yearOfBirth: Int? = null
)


