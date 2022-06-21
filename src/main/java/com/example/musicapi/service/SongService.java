package com.example.musicapi.service;

import com.example.musicapi.model.Result;
import com.example.musicapi.model.Song;

import java.util.List;

public interface SongService {

    Song findSong(String title);
    void addToFavorite(Result result);
    List<Result> getFavorites();
}
