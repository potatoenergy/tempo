package com.cappielloantonio.play.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cappielloantonio.play.model.Artist;
import com.cappielloantonio.play.model.Genre;
import com.cappielloantonio.play.model.Song;
import com.cappielloantonio.play.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;

public class SongListPageViewModel extends AndroidViewModel {
    private SongRepository songRepository;

    private LiveData<List<Song>> songList;

    public String title;
    public Genre genre;
    public Artist artist;
    public ArrayList<String> filters = new ArrayList<>();
    public ArrayList<String> filterNames = new ArrayList<>();
    public int year = 0;

    public SongListPageViewModel(@NonNull Application application) {
        super(application);

        songRepository = new SongRepository(application);
    }

    public LiveData<List<Song>> getSongList() {
        switch (title) {
            case Song.RECENTLY_PLAYED:
                songList = songRepository.getListLiveRecentlyPlayedSampleSong(100);
                break;
            case Song.MOST_PLAYED:
                songList = songRepository.getListLiveMostPlayedSampleSong(100);
                break;
            case Song.RECENTLY_ADDED:
                songList = songRepository.getListLiveRecentlyAddedSampleSong(100);
                break;
            case Song.BY_GENRE:
                songList = songRepository.getListLiveSongByGenre(genre.getId());
                break;
            case Song.BY_ARTIST:
                songList = songRepository.getArtistListLiveTopSong(artist.getId());
                break;
            case Song.BY_GENRES:
                songList = songRepository.getFilteredListLiveSong(filters);
                break;
            case Song.BY_YEAR:
                songList = songRepository.getSongByYearListLive(year);
                break;
            case Song.IS_FAVORITE:
                songList = songRepository.getListLiveFavoritesSong();
                break;
        }

        return songList;
    }

    public String getFiltersTitle() {
        return TextUtils.join(", ", filterNames);
    }
}