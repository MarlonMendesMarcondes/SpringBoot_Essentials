package academy.devdojo.CursoSpringBoot2Essentials.util;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Dragonball Z kai")
                .build();
    }
    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Dragonball Z kai")
                .id(1L)
                .build();
    }
    public static Anime createValidUpdatedAnime(){
        return Anime.builder()
                .name("Dragonball Z kai")
                .id(1L)
                .build();
    }
}
