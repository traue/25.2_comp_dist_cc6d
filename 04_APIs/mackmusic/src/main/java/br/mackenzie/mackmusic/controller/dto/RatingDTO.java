package br.mackenzie.mackmusic.controller.dto;

import br.mackenzie.mackmusic.model.Rating;
import br.mackenzie.mackmusic.model.Song;
import br.mackenzie.mackmusic.model.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record RatingDTO(
        UUID id,

        @NotNull(message = "Usuário é obrigatório")
        UUID userId,

        @NotNull(message = "Musica obrigatório")
        UUID songId,

        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 5, message = "A nota máxima é 5")
        int score,

        @Size(max = 100, message = "A avaliação deve ter no máximo 100 caracteres)")
        String review,

        LocalDateTime ratedAt
) {

        public static RatingDTO fromEntity(Rating rating) {
                return new RatingDTO(
                        rating.getId(),
                        rating.getUser().getId(),
                        rating.getSong().getId(),
                        rating.getScore(),
                        rating.getReview(),
                        rating.getRatedAt()
                );
        }

        public Rating toEntity(User user, Song song) {
                Rating rating = new Rating();
                rating.setId(this.id());
                rating.setUser(user);
                rating.setSong(song);
                rating.setScore(this.score());
                rating.setReview(this.review());
                rating.setRatedAt(this.ratedAt() != null ? this.ratedAt() : LocalDateTime.now());
                return rating;
        }

}
