package br.mackenzie.mackmusic.controller.dto;

import br.mackenzie.mackmusic.model.Artist;
import br.mackenzie.mackmusic.model.Song;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ArtistDTO(

        UUID id,

        @NotBlank(message = "Nome do artista é obrigatório")
        @Size(min = 1, max = 100, message = "Quantidade de caracteres inválida para o nome do artista")
        String name,

        @NotBlank(message = "País de origem é obrigatório")
        @Size(min = 2, max = 56, message = "Nome do país inválido")
        String country,

        List<UUID> songIds

) {
    public static ArtistDTO fromEntity(Artist artist) {
        return new ArtistDTO(
                artist.getId(),
                artist.getName(),
                artist.getCountry(),
                artist.getSongs() != null
                        ? artist.getSongs().stream()
                        .map(Song::getId)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }

    public Artist toEntity(List<Song> songs) {
        Artist artist = new Artist();
        artist.setId(this.id());
        artist.setName(this.name());
        artist.setCountry(this.country());
        artist.setSongs(songs);
        return artist;
    }
}