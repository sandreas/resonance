package com.pilabor.resonance.model

data class MyMediaSource(
    val id: String,
    val name: String,
    val api: String
)


val sampleMediaSources = List(100) { it ->
    MyMediaSource(
        id="$it",
        name="Name $it",
        api="AudioBookShelf"
    )
}