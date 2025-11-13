package br.mackenzie.mackmusic.service;

import br.mackenzie.mackmusic.controller.dto.AlbumDTO;
import br.mackenzie.mackmusic.controller.dto.SongDTO;
import br.mackenzie.mackmusic.model.Album;
import br.mackenzie.mackmusic.model.Artist;
import br.mackenzie.mackmusic.model.Song;
import br.mackenzie.mackmusic.repository.AlbumRepository;
import br.mackenzie.mackmusic.repository.ArtistRepository;
import br.mackenzie.mackmusic.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public List<AlbumDTO> getAlbums(String name, Year year, String genre) {

        List<Album> albums;

        if (name != null) {
            albums = albumRepository.findByNameContainingIgnoreCase(name);
        } else if (year != null) {
            albums = albumRepository.findByReleaseYear(year);
        } else if (genre != null) {
            albums = albumRepository.findBySongs_GenreIgnoreCase(genre);
        } else {
            albums = albumRepository.findAll();
        }

        return albums.stream().map(AlbumDTO::fromEntity).toList();
    }

    public AlbumDTO getAlbumById(UUID id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found with id: " + id));
        return AlbumDTO.fromEntity(album);
    }

    @Transactional
    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        // First, create all songs
        List<Song> songs = albumDTO.songs().stream()
                .map(songDTO -> {
                    Artist artist = artistRepository.findById(songDTO.artistId())
                            .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + songDTO.artistId()));

                    Song song = songDTO.toEntity(artist, new ArrayList<>());
                    return songRepository.save(song);
                })
                .collect(Collectors.toList());

        // Create the album
        Album album = new Album();
        album.setName(albumDTO.name());
        album.setReleaseYear(albumDTO.releaseYear());

        // Set the songs and establish bidirectional relationship
        album.setSongs(songs);
        songs.forEach(song -> {
            if (song.getAlbums() == null) {
                song.setAlbums(new ArrayList<>());
            }
            song.getAlbums().add(album);
        });

        Album savedAlbum = albumRepository.save(album);

        // Save the updated songs to ensure the relationships are persisted
        songRepository.saveAll(songs);

        return AlbumDTO.fromEntity(savedAlbum);
    }

    @Transactional
    public AlbumDTO updateAlbum(UUID id, AlbumDTO albumDTO) {
        Album existingAlbum = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found with id: " + id));

        List<Song> songs = songRepository.findAllById(
                albumDTO.songs().stream()
                        .map(SongDTO::id)
                        .toList()
        );

        existingAlbum.setName(albumDTO.name());
        existingAlbum.setReleaseYear(albumDTO.releaseYear());
        existingAlbum.setSongs(songs);

        Album updatedAlbum = albumRepository.save(existingAlbum);
        return AlbumDTO.fromEntity(updatedAlbum);
    }

    @Transactional
    public void deleteAlbum(UUID id) throws EntityNotFoundException {
        if (!albumRepository.existsById(id)) {
            throw new EntityNotFoundException("Album not found with id: " + id);
        }
        albumRepository.deleteById(id);
    }

}
