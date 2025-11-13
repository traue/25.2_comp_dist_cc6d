//package br.mackenzie.mackmusic;
//
//import br.mackenzie.mackmusic.model.*;
//import br.mackenzie.mackmusic.repository.*;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.time.Year;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//class MackmusicApplicationTests {
//
//	 @Autowired private SongRepository songRepository;
//	 @Autowired private ArtistRepository artistRepository;
//	 @Autowired	private AlbumRepository albumRepository;
//	 @Autowired private RatingRepository ratingRepository;
//	 @Autowired private UserRepository userRepository;
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Test
//	@Transactional
//	void testUserRating() {
//		// Cria um usuário
//		User user = new User(null, "Fulaninho de Tal", "flaninho@mackenzie.br", "Pass123");
//		userRepository.save(user);
//
//		Artist artist = new Artist(null, "The Beatles", "UK", new ArrayList<>());
//		artistRepository.save(artist);
//
//		//Song song = new Song(null, "Let It Be", "Rock", Year.of(1970), artist, new ArrayList<>());
//		//songRepository.save(song);
//
//		//Rating rating = new Rating(null, user, song, 5, "Amazing song!", LocalDateTime.now());
//		//ratingRepository.save(rating);
//
//		List<Rating> userRatings = ratingRepository.findByUserId(user.getId());
//		assertFalse(userRatings.isEmpty());
//		assertEquals(5, userRatings.getFirst().getScore());
//	}
//
//	@Test
//	@Transactional
//	void testSongAndAlbum() {
//		Artist artist = new Artist(null, "The Beatles", "UK", new ArrayList<>());
//		artistRepository.save(artist);
//
//		Album album1 = new Album(null, "Abbey Road", Year.of(1969), new ArrayList<>());
//		Album album2 = new Album(null, "The White Album", Year.of(1968), new ArrayList<>());
//		albumRepository.saveAll(List.of(album1, album2));
//
//		Song song1 = new Song(null, "Come Together", "Rock", Year.of(1969), artist, new ArrayList<>());
//		Song song2 = new Song(null, "While my guitar gently weaps", "Rock", Year.of(1968), artist, new ArrayList<>());
//		Song song3 = new Song(null, "Revolution", "Rock", Year.of(1968), artist, new ArrayList<>());
//
//		album1.addSong(song1);
//		album1.addSong(song3);
//		album2.addSong(song2);
//		album2.addSong(song3);
//		albumRepository.saveAll(List.of(album1, album2));
//
//		Album foundAlbum1 = albumRepository.findById(album1.getId()).orElse(null);
//		Album foundAlbum2 = albumRepository.findById(album2.getId()).orElse(null);
//
//		assertNotNull(foundAlbum1);
//		assertNotNull(foundAlbum2);
//		assertNotNull(foundAlbum1.getSongs());
//		assertNotNull(foundAlbum2.getSongs());
//		assertEquals(2, foundAlbum1.getSongs().size());
//		assertEquals(2, foundAlbum2.getSongs().size());
//	}
//}
