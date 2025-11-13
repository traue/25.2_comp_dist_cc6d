package br.mackenzie.mackmusic.controller;

import br.mackenzie.mackmusic.controller.dto.RatingDTO;
import br.mackenzie.mackmusic.model.Rating;
import br.mackenzie.mackmusic.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable UUID id) {
        return ResponseEntity.ok(ratingService.getRatingById(id));
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<Rating>> getRatingsBySongId(@PathVariable UUID songId) {
        return ResponseEntity.ok(ratingService.findRatingsBySongId(songId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(ratingService.findRatingsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody @Valid RatingDTO ratingDTO) {
        return ResponseEntity.ok(ratingService.createRating(ratingDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> updateRating(
            @PathVariable UUID id,
            @RequestBody @Valid RatingDTO ratingDTO) {
        return ResponseEntity.ok(ratingService.updateRating(id, ratingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable UUID id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}