package academy.devdojo.CursoSpringBoot2Essentials.repository;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimeCreator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@DisplayName("Tests for anime repository")
@Log4j2
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;
    @Test
    @DisplayName("Save persistent anime when sucessful")
    void save_PersistAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertNotNull(animeSaved);

        Assertions.assertNotNull(animeSaved.getId());

        Assertions.assertEquals(animeSaved.getName(),animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Save updates anime when sucessful")
    void save_UpdatesAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Overlord");

        Anime animeupdated = this.animeRepository.save(animeSaved);

        log.info(animeupdated.getName());

        Assertions.assertNotNull(animeupdated);

        Assertions.assertNotNull(animeupdated.getId());

        Assertions.assertEquals(animeupdated.getName(),animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Delete remove anime when sucessful")
    void Delete_RemoveAnime_WhenSucessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertTrue(animeOptional.isEmpty());
    }

    @Test
    @DisplayName("Find By Name returns list of anime when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertFalse(animes.isEmpty());
        Assertions.assertTrue(animes.contains(animeSaved));

    }

    @Test
    @DisplayName("Find By Name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        List<Anime> animes = this.animeRepository.findByName("xaxa");

        Assertions.assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("Save Throw ConstraintValidationException when name is empty")
    void save_ThrowConstraintValidationException_WhenNameIsEmpty(){
        Anime anime = new Anime();
//      assertThrows(ConstraintViolationException.class,
//                () -> this.animeRepository.save(anime));

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");

    }


}