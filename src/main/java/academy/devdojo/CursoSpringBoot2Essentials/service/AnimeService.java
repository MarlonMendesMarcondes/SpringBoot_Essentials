package academy.devdojo.CursoSpringBoot2Essentials.service;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import academy.devdojo.CursoSpringBoot2Essentials.exception.BadRequestException;
import academy.devdojo.CursoSpringBoot2Essentials.mapper.AnimeMapper;
import academy.devdojo.CursoSpringBoot2Essentials.repository.AnimeRepository;
import academy.devdojo.CursoSpringBoot2Essentials.requests.AnimePostRequestBody;
import academy.devdojo.CursoSpringBoot2Essentials.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime ID not Found"));
    }

    public Anime save(AnimePostRequestBody animepostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animepostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}
