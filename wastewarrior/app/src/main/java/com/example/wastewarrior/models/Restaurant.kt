package com.example.wastewarrior.models

data class Restaurant(val id: String,  val name: String, val address: HashMap<String, Any>, val surprises: List<SurpriseBag>)
