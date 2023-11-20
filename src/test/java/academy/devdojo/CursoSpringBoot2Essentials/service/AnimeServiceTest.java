package academy.devdojo.CursoSpringBoot2Essentials.service;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import academy.devdojo.CursoSpringBoot2Essentials.exception.BadRequestException;
import academy.devdojo.CursoSpringBoot2Essentials.repository.AnimeRepository;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimeCreator;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimePutRequestBodyCreator;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidUpdatedAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertNotNull(animePage);

        Assertions.assertFalse(animePage.toList().isEmpty());

        Assertions.assertEquals(1, animePage.toList().size());

        Assertions.assertEquals(animePage.toList().get(0).getName(), expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of anime when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.listAllNonPageable();
        Assertions.assertNotNull(animes);
        Assertions.assertFalse(animes.isEmpty());
        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(animes.get(0).getName(), (expectedName));
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(BadRequestException.class,
                ()->this.animeService.findByIdOrThrowBadRequestException(1));

    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findByIdOrThrowBadRequestException(1);

        Assertions.assertNotNull(anime);
        Assertions.assertEquals(anime.getId(), expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertNotNull(animes);
        Assertions.assertFalse(animes.isEmpty());
        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(animes.get(0).getName(), (expectedName));
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("anime");

        Assertions.assertNotNull(animes);
        Assertions.assertTrue(animes.isEmpty());
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {

        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertNotNull(anime);
        Assertions.assertEquals(anime, AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {

        Assertions.assertDoesNotThrow(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {

        Assertions.assertDoesNotThrow(() -> animeService.delete(1));

    }

}
