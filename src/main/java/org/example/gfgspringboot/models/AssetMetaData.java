package org.example.gfgspringboot.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.sql.Date;

@Embeddable
public class AssetMetaData implements Serializable {
    private String author;
    private String genre;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
