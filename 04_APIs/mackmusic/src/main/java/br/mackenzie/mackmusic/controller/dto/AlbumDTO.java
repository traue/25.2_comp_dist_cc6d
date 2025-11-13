package br.mackenzie.mackmusic.controller.dto;

import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.mackenzie.mackmusic.model.Album;
import br.mackenzie.mackmusic.model.Song;
import jakarta.validation.constraints.*;

public record AlbumDTO(
        UUID id,
        @NotBlank(message = "Nome do album é obrigatório")
        @Size(min = 1, max = 50, message = "Qunatidade de caracteres inválida para o nome do album")
        String name,

        Year releaseYear,
        List<SongDTO> songs
) {

    public static AlbumDTO fromEntity(Album album) {
        return new AlbumDTO(
                album.getId(),
                album.getName(),
                album.getReleaseYear(),
                album.getSongs().stream()
                        .map(SongDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public Album toEntity(List<Song> songs) {
        Album album = new Album();
        album.setId(this.id()); // pode ser null na criação
        album.setName(this.name());
        album.setReleaseYear(this.releaseYear());
        album.setSongs(songs);
        return album;
    }
}
