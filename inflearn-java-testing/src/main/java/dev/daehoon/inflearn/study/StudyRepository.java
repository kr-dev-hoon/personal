package dev.daehoon.inflearn.study;

import dev.daehoon.inflearn.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<Study> findById(long id);

    List<Study> findAllById(List<Long> ids);

    void validate(long id);

    void notify(long id);
    void notify2(long id);
}
