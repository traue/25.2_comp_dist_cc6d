package br.mackenzie.mackmusic.config;

import br.mackenzie.mackmusic.model.*;
import br.mackenzie.mackmusic.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;


    @PostConstruct
    public void init() {

        if (artistRepository.count() > 0 || albumRepository.count() > 0 || songRepository.count() > 0) {
            return;
        }

        Artist beatles = new Artist();
        beatles.setName("The Beatles");
        beatles.setCountry("UK");

        Artist u2 = new Artist();
        u2.setName("U2");
        u2.setCountry("Ireland");

        artistRepository.saveAll(List.of(beatles, u2));

        Album abbeyRoad = new Album();
        abbeyRoad.setName("Abbey Road");
        abbeyRoad.setReleaseYear(Year.of(1969));

        Album joshuaTree = new Album();
        joshuaTree.setName("The Joshua Tree");
        joshuaTree.setReleaseYear(Year.of(1987));

        Album revolver = new Album();
        revolver.setName("Revolver");
        revolver.setReleaseYear(Year.of(1966));

        Album war = new Album();
        war.setName("War");
        war.setReleaseYear(Year.of(1983));

        albumRepository.saveAll(List.of(abbeyRoad, joshuaTree, revolver, war));

        Song comeTogether = new Song();
        comeTogether.setTitle("Come Together");
        comeTogether.setGenre("Rock");
        comeTogether.setReleaseYear(Year.of(1969));
        comeTogether.setArtist(beatles);
        comeTogether.getAlbums().add(abbeyRoad);

        Song withOrWithoutYou = new Song();
        withOrWithoutYou.setTitle("With or Without You");
        withOrWithoutYou.setGenre("Rock");
        withOrWithoutYou.setReleaseYear(Year.of(1987));
        withOrWithoutYou.setArtist(u2);
        withOrWithoutYou.getAlbums().add(joshuaTree);

        // Adicionando mais músicas
        Song eleanorRigby = new Song();
        eleanorRigby.setTitle("Eleanor Rigby");
        eleanorRigby.setGenre("Rock");
        eleanorRigby.setReleaseYear(Year.of(1966));
        eleanorRigby.setArtist(beatles);
        eleanorRigby.getAlbums().add(revolver);

        Song yellowSubmarine = new Song();
        yellowSubmarine.setTitle("Yellow Submarine");
        yellowSubmarine.setGenre("Rock");
        yellowSubmarine.setReleaseYear(Year.of(1968));
        yellowSubmarine.setArtist(beatles);
        yellowSubmarine.getAlbums().add(revolver);

        Song sundayBloodySunday = new Song();
        sundayBloodySunday.setTitle("Sunday Bloody Sunday");
        sundayBloodySunday.setGenre("Rock");
        sundayBloodySunday.setReleaseYear(Year.of(1983));
        sundayBloodySunday.setArtist(u2);
        sundayBloodySunday.getAlbums().add(war);

        songRepository.saveAll(List.of(eleanorRigby, yellowSubmarine, sundayBloodySunday));
        songRepository.saveAll(List.of(comeTogether, withOrWithoutYou));

        // Criação de usuários
        User user1 = new User();
        user1.setName("Thiago Traue");
        user1.setEmail("thiago.traue@mackenzie.br");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setName("Fulaninho");
        user2.setEmail("fulaninho@mack.br");
        user2.setPassword("123456");

        User user3 = new User();
        user3.setName("Beltraninho");
        user3.setEmail("beltrano@mack.br");
        user3.setPassword("Senha123");

        userRepository.saveAll(List.of(user1, user2, user3));

        // Criação de avaliações
        Rating rating1 = new Rating();
        rating1.setUser(user1);
        rating1.setSong(comeTogether);
        rating1.setReview("Love that song!");
        rating1.setScore(5);

        Rating rating2 = new Rating();
        rating2.setUser(user2);
        rating2.setSong(withOrWithoutYou);
        rating2.setReview("My favourite song!");
        rating2.setScore(4);

        Rating rating3 = new Rating();
        rating3.setUser(user1);
        rating3.setSong(eleanorRigby);
        rating3.setReview("Very nice song. Not my favourite, but i like");
        rating3.setScore(4);

        Rating rating4 = new Rating();
        rating4.setUser(user2);
        rating4.setSong(yellowSubmarine);
        rating4.setReview("The problem with this song is that it ends");
        rating4.setScore(5);

        Rating rating5 = new Rating();
        rating5.setUser(user1);
        rating5.setSong(sundayBloodySunday);
        rating5.setReview("Not in my main list but its a nice song");
        rating5.setScore(3);

        Rating rating6 = new Rating();
        rating6.setUser(user3);
        rating6.setSong(sundayBloodySunday);
        rating6.setReview("Not for me");
        rating6.setScore(2);

        Rating rating7 = new Rating();
        rating7.setUser(user3);
        rating7.setSong(yellowSubmarine);
        rating7.setReview("Should listen everyday!!");
        rating7.setScore(5);

        ratingRepository.saveAll(List.of(rating1, rating2, rating3, rating4, rating5, rating6, rating7));


    }

}
