package br.mackenzie.mackmusic.controller;

import br.mackenzie.mackmusic.controller.dto.RatingDTO;
import br.mackenzie.mackmusic.controller.dto.SongDTO;
import br.mackenzie.mackmusic.model.Song;
import br.mackenzie.mackmusic.service.RatingService;
import br.mackenzie.mackmusic.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<SongDTO>> finSong(
            @RequestParam(value = "title", required = false) String title
    ) {
        List<Song> result = songService.findByTitle(title);

        List<SongDTO> songDTOS = result.stream().map(
                song -> {

                    List<RatingDTO> ratings = ratingService.findRatingsBySongId(song.getId())
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
                            ratings
                    );
                }
        ).toList();

        return ResponseEntity.ok(songDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable UUID id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PostMapping
    public ResponseEntity<SongDTO> createSong(@RequestBody @Valid SongDTO songDTO) {
        return ResponseEntity.ok(songService.createSong(songDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(
            @PathVariable UUID id,
            @RequestBody @Valid SongDTO songDTO) {
        return ResponseEntity.ok(songService.updateSong(id, songDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable UUID id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
