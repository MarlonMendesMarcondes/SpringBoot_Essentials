package academy.devdojo.CursoSpringBoot2Essentials.repository;

import academy.devdojo.CursoSpringBoot2Essentials.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime,Long> {
    List<Anime> findByName (String nome);
}
