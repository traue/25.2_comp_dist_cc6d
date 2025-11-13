package br.mackenzie.mackmusic.repository;

import br.mackenzie.mackmusic.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

    List<Album> findByNameContainingIgnoreCase(String name);
    List<Album> findByReleaseYear(Year year);
    List<Album> findBySongs_GenreIgnoreCase(String genre);

}
