package com.example.musicapi.service;

import com.example.musicapi.model.Result;
import com.example.musicapi.model.Song;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;

@Service
public class SongServiceImpl implements SongService {

    private List<Result> favorites;

    public SongServiceImpl() {
        favorites = new ArrayList<>();
    }

    @Override
    public Song findSong(String title) {
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        Song song = restTemplate.getForObject("https://itunes.apple.com/search?term=" + title + "&country=US&limit=50", Song.class);

        return song;
    }

    @Override
    public void addToFavorite(Result result) {
        favorites.add(result);
    }

    @Override
    public List<Result> getFavorites() {
        return favorites;
    }
}
