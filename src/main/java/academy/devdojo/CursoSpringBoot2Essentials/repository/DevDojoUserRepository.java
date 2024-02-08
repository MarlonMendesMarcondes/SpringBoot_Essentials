package academy.devdojo.CursoSpringBoot2Essentials.repository;

import academy.devdojo.CursoSpringBoot2Essentials.domain.DevDojoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {

    DevDojoUser findByUsername(String username);
}
