package br.mackenzie.mackmusic.service;

import br.mackenzie.mackmusic.controller.dto.RatingDTO;
import br.mackenzie.mackmusic.model.Rating;
import br.mackenzie.mackmusic.model.Song;
import br.mackenzie.mackmusic.model.User;
import br.mackenzie.mackmusic.repository.RatingRepository;
import br.mackenzie.mackmusic.repository.SongRepository;
import br.mackenzie.mackmusic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;


    public List<Rating> findRatingsBySongId(UUID songId) {
        return ratingRepository.findBySongId(songId);
    }

    public List<RatingDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(RatingDTO::fromEntity)
                .toList();
    }

    public RatingDTO getRatingById(UUID id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id));
        return RatingDTO.fromEntity(rating);
    }

//    public List<RatingDTO> findRatingsBySongId(UUID songId) {
//        return ratingRepository.findBySongId(songId).stream()
//                .map(RatingDTO::fromEntity)
//                .toList();
//    }

    public List<RatingDTO> findRatingsByUserId(UUID userId) {
        return ratingRepository.findByUserId(userId).stream()
                .map(RatingDTO::fromEntity)
                .toList();
    }

    @Transactional
    public RatingDTO createRating(RatingDTO ratingDTO) {
        User user = userRepository.findById(ratingDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + ratingDTO.userId()));

        Song song = songRepository.findById(ratingDTO.songId())
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + ratingDTO.songId()));

        Rating rating = ratingDTO.toEntity(user, song);
        Rating savedRating = ratingRepository.save(rating);
        return RatingDTO.fromEntity(savedRating);
    }

    @Transactional
    public RatingDTO updateRating(UUID id, RatingDTO ratingDTO) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found with id: " + id));

        User user = userRepository.findById(ratingDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + ratingDTO.userId()));

        Song song = songRepository.findById(ratingDTO.songId())
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + ratingDTO.songId()));

        existingRating.setUser(user);
        existingRating.setSong(song);
        existingRating.setScore(ratingDTO.score());
        existingRating.setReview(ratingDTO.review());
        existingRating.setRatedAt(ratingDTO.ratedAt());

        Rating updatedRating = ratingRepository.save(existingRating);
        return RatingDTO.fromEntity(updatedRating);
    }

    @Transactional
    public void deleteRating(UUID id) {
        if (!ratingRepository.existsById(id)) {
            throw new EntityNotFoundException("Rating not found with id: " + id);
        }
        ratingRepository.deleteById(id);
    }

}
