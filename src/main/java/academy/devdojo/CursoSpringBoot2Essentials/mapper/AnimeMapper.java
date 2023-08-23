package academy.devdojo.CursoSpringBoot2Essentials.mapper;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import academy.devdojo.CursoSpringBoot2Essentials.requests.AnimePostRequestBody;
import academy.devdojo.CursoSpringBoot2Essentials.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePostRequestBody);
}
