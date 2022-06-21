package com.example.musicapi.controller;


import com.example.musicapi.model.Result;
import com.example.musicapi.model.Song;
import com.example.musicapi.service.SongService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SongController {
    private SongService songService;

    @Autowired
    public SongController(SongService songService){
        this.songService = songService;
    }

    @GetMapping("/")
    public String getSong(Model model, @RequestParam(required = false, defaultValue = "") String term){



        Song song = new Song();

        if (!term.isEmpty()){
            song = songService.findSong(term);
        }

        model.addAttribute("song", song);
        return "home";
    }

    @GetMapping("/track/{trackId}")
    public String getDetails(Model model, @PathVariable String trackId){
        model.addAttribute("favourites", songService.getFavorites());
        model.addAttribute("editedSong", songService.findSong(trackId).getResults().get(0));
        return "song-details";
    }

    @GetMapping("/favourites")
    public String getFavourites(Model model){

        model.addAttribute("favourites", songService.getFavorites());

        return "favourites";
    }

    @GetMapping("/track/{trackId}/addFavorite")
    public String setFavorite(@PathVariable String trackId) {
        Result track = songService.findSong(trackId).getResults().get(0);
        songService.addToFavorite(track);
        return "redirect:/track/{trackId}";
    }

}
