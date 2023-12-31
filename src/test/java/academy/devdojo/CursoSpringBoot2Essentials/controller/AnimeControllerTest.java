package academy.devdojo.CursoSpringBoot2Essentials.controller;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import academy.devdojo.CursoSpringBoot2Essentials.requests.AnimePostRequestBody;
import academy.devdojo.CursoSpringBoot2Essentials.requests.AnimePutRequestBody;
import academy.devdojo.CursoSpringBoot2Essentials.service.AnimeService;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimeCreator;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimePutRequestBodyCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;
    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }
    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnListOfAnimesInsidePageObject_WhenSucessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertNotNull(animePage);

        Assertions.assertFalse(animePage.toList().isEmpty());

        Assertions.assertEquals(1, animePage.toList().size());

        Assertions.assertEquals(animePage.toList().get(0).getName(),expectedName);
    }
    @Test
    @DisplayName("listAll returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.listAll().getBody();
        Assertions.assertNotNull(animes);
        Assertions.assertFalse(animes.isEmpty());
        Assertions.assertEquals(1,animes.size());

        Assertions.assertEquals(animes.get(0).getName(),(expectedName));
    }
    @Test
    @DisplayName("findById returns anime when successful")
    void findbyID_ReturnAnimes_WhenSucessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(1).getBody();

        Assertions.assertNotNull(anime);
        Assertions.assertEquals(anime.getId(),expectedId);
    }
    @Test
    @DisplayName("findByName returns a list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertNotNull(animes);
        Assertions.assertFalse(animes.isEmpty());
        Assertions.assertEquals(1, animes.size());

        Assertions.assertEquals(animes.get(0).getName(),(expectedName));
    }
    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertNotNull(animes);
        Assertions.assertTrue(animes.isEmpty());
    }
    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertNotNull(anime);
        Assertions.assertEquals(anime,AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){

        Assertions.assertDoesNotThrow(() ->animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()));

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertNotNull(entity);

        Assertions.assertEquals(entity.getStatusCode(),(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){

        Assertions.assertDoesNotThrow(() ->animeController.delete(1));

        ResponseEntity<Void> entity = animeController.delete(1);

        Assertions.assertNotNull(entity);

        Assertions.assertEquals(entity.getStatusCode(),(HttpStatus.NO_CONTENT));
    }

}