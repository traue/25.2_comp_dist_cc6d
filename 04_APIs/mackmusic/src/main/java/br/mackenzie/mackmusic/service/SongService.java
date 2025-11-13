package br.mackenzie.mackmusic.service;

import br.mackenzie.mackmusic.controller.dto.SongDTO;
import br.mackenzie.mackmusic.model.Artist;
import br.mackenzie.mackmusic.model.Song;
import br.mackenzie.mackmusic.repository.AlbumRepository;
import br.mackenzie.mackmusic.repository.ArtistRepository;
import br.mackenzie.mackmusic.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public List<Song> findByTitle(String title) {
        var song = new Song();
        song.setTitle(title);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id")
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Song> example = Example.of(song, matcher);
        return songRepository.findAll(example);
    }

    public SongDTO getSongById(UUID id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));
        return SongDTO.fromEntity(song);
    }

    @Transactional
    public SongDTO createSong(SongDTO songDTO) {
        Artist artist = artistRepository.findById(songDTO.artistId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + songDTO.artistId()));

        // The song is new, so it starts without albums
        Song song = songDTO.toEntity(artist, List.of());
        Song savedSong = songRepository.save(song);
        return SongDTO.fromEntity(savedSong);
    }

    @Transactional
    public SongDTO updateSong(UUID id, SongDTO songDTO) {
        Song existingSong = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));

        Artist artist = artistRepository.findById(songDTO.artistId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + songDTO.artistId()));

        // Keep the existing albums relationship
        existingSong.setTitle(songDTO.title());
        existingSong.setGenre(songDTO.genre());
        existingSong.setReleaseYear(songDTO.releaseYear());
        existingSong.setArtist(artist);

        Song updatedSong = songRepository.save(existingSong);
        return SongDTO.fromEntity(updatedSong);
    }

    @Transactional
    public void deleteSong(UUID id) {
        if (!songRepository.existsById(id)) {
            throw new EntityNotFoundException("Song not found with id: " + id);
        }
        songRepository.deleteById(id);
    }
}