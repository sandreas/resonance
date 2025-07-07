package com.pilabor.resonance.mediaSource.api

enum class MediaSourceAction {
    None,
    Remove,
    Insert,
    Play,
    Pause,
    Stop,
    MediaItemChanged,
    PositionChanged,

    Next,
    Previous,
    NextChapter,
    PreviousChapter,
    FastForward,
    Rewind,
    StepForward,
    StepBack,

    AddToFavorites,

    Loading,
    Buffering,
    Fail
}