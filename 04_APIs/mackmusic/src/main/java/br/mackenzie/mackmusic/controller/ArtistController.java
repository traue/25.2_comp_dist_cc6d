package br.mackenzie.mackmusic.controller;

import br.mackenzie.mackmusic.controller.dto.ArtistDTO;
import br.mackenzie.mackmusic.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistDTO>> getArtists(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country) {
        return ResponseEntity.ok(artistService.getArtists(name, country));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable UUID id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody @Valid ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.createArtist(artistDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(
            @PathVariable UUID id,
            @RequestBody @Valid ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.updateArtist(id, artistDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable UUID id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}