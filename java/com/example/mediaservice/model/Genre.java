package com.example.mediaservice.model;

public enum Genre {
    THRILLER("Триллер"),
    ADVENTURE("Приключенческий"),
    BLOCKBUSTER("Боевик"),
    DRAMA("Драма"),
    HISTORICAL("Исторический"),
    FANTASY("Фантастика"),
    HORROR("Фильм ужасов"),
    COMEDY("Комедия"),
    ROMANCE("Фильм о любви");
    private final String genreText;


    Genre(String genreText) {
        this.genreText = genreText;
    }

    public String getGenreText(){
        return this.genreText;
    }
}
