package academy.devdojo.CursoSpringBoot2Essentials.controller;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import academy.devdojo.CursoSpringBoot2Essentials.service.AnimeService;
import academy.devdojo.CursoSpringBoot2Essentials.util.AnimeCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;
    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidUpdatedAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);
    }
    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnListOfAnimesInsidePageObject_WhenSucessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertNotNull(animePage);

        Assertions.assertFalse(animePage
                .toList()
                .isEmpty());

        Assertions.assertEquals(animePage.toList().get(0).getName(),expectedName);
    }
}