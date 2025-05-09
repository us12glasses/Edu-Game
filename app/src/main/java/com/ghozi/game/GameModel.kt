package com.ghozi.game

data class GameModel(
    val id : String,
    val title : String,
    val subtitle : String,
    val time : String,
    val questionList : List<QuestionModel>
) {
    constructor() : this("", "", "", "", emptyList())
}

data class QuestionModel(
    val question : String,
    val options : List<String>,
    val correct : String
) {
    constructor() : this("", emptyList(), "")
}