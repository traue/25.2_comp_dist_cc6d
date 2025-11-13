package br.mackenzie.mackmusic.controller.dto;

import br.mackenzie.mackmusic.model.Album;
import br.mackenzie.mackmusic.model.Artist;
import br.mackenzie.mackmusic.model.Song;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.Year;
import java.util.List;
import java.util.UUID;

public record SongDTO(
        UUID id,
        @NotBlank(message = "Titulo da música não pode ser nulo")
        @Size(min = 1, max = 100, message = "Quantidade de caracteres inválida para o título da música")
        String title,

        @NotNull(message = "O gênero é obrigatório")
        String genre,

        @Past(message = "Como pode uma música ser escrita no futuro")
        Year releaseYear,

        @NotNull(message = "Obrigatório o ID do artista")
        UUID artistId,

        String artistName,

        List<RatingDTO> ratings
) {
    public static SongDTO fromEntity(Song song) {

        List<RatingDTO> ratings = song.getRatings()
                .stream()
                .map(rating -> new RatingDTO(
                        rating.getId(),
                        rating.getUser().getId(),
                        rating.getSong().getId(),
                        rating.getScore(),
                        rating.getReview(),
                        rating.getRatedAt()))
                .toList();

        return new SongDTO(
                song.getId(),
                song.getTitle(),
                song.getGenre(),
                song.getReleaseYear(),
                song.getArtist().getId(),
                song.getArtist().getName(),
                ratings);
    }

    public Song toEntity(Artist artist, List<Album> albums) {
        Song song = new Song();
        song.setId(this.id());
        song.setTitle(this.title());
        song.setGenre(this.genre());
        song.setReleaseYear(this.releaseYear());
        song.setArtist(artist);
        song.setAlbums(albums);
        return song;
    }
}
