package br.mackenzie.mackmusic.controller;

import br.mackenzie.mackmusic.controller.dto.AlbumDTO;
import br.mackenzie.mackmusic.service.AlbumService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAlbums(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre
    ) {
        Year releaseYear = year != null ? Year.of(year) : null;
        List<AlbumDTO> albumDTOS = albumService.getAlbums(name, releaseYear, genre);
        return ResponseEntity.ok(albumDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable UUID id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody @Valid AlbumDTO albumDTO) {
        return ResponseEntity.ok(albumService.createAlbum(albumDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(
            @PathVariable UUID id,
            @RequestBody @Valid AlbumDTO albumDTO) {
        return ResponseEntity.ok(albumService.updateAlbum(id, albumDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id) {
        try {
            albumService.deleteAlbum(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
