package br.mackenzie.mackmusic.service;

import br.mackenzie.mackmusic.controller.dto.ArtistDTO;
import br.mackenzie.mackmusic.model.Artist;
import br.mackenzie.mackmusic.model.Song;
import br.mackenzie.mackmusic.repository.ArtistRepository;
import br.mackenzie.mackmusic.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistDTO::fromEntity)
                .toList();
    }

    public ArtistDTO getArtistById(UUID id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + id));
        return ArtistDTO.fromEntity(artist);
    }

    public List<ArtistDTO> getArtists(String name, String country) {
        List<Artist> artists;

        if (name != null && country != null) {
            artists = artistRepository.findByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(name, country);
        } else if (name != null) {
            artists = artistRepository.findByNameContainingIgnoreCase(name);
        } else if (country != null) {
            artists = artistRepository.findByCountryContainingIgnoreCase(country);
        } else {
            artists = artistRepository.findAll();
        }

        return artists.stream()
                .map(ArtistDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ArtistDTO createArtist(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.name());
        artist.setCountry(artistDTO.country());
        artist.setSongs(new ArrayList<>()); // Initialize with empty list

        Artist savedArtist = artistRepository.save(artist);
        return ArtistDTO.fromEntity(savedArtist);
    }

    @Transactional
    public ArtistDTO updateArtist(UUID id, ArtistDTO artistDTO) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + id));

        List<Song> songs = songRepository.findAllById(artistDTO.songIds());

        existingArtist.setName(artistDTO.name());
        existingArtist.setCountry(artistDTO.country());
        existingArtist.setSongs(songs);

        Artist updatedArtist = artistRepository.save(existingArtist);
        return ArtistDTO.fromEntity(updatedArtist);
    }

    @Transactional
    public void deleteArtist(UUID id) {
        if (!artistRepository.existsById(id)) {
            throw new EntityNotFoundException("Artist not found with id: " + id);
        }
        artistRepository.deleteById(id);
    }
}