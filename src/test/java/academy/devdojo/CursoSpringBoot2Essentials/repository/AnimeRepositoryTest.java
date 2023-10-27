package academy.devdojo.CursoSpringBoot2Essentials.repository;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DisplayName("Save Created animes when sucessful")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;
    @Test
    void save_PersistAnime_WhenSucessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        Assertions.assertNotNull(animeSaved);
        Assertions.assertNotNull(animeSaved.getId());
        Assertions.assertEquals(animeSaved.getName(),animeToBeSaved.getName());
    }
    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}