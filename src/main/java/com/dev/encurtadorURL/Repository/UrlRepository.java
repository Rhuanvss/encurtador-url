package com.dev.encurtadorURL.Repository;

import com.dev.encurtadorURL.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByUrlEncurtada(String urlEncurtada);

    boolean existsByUrlEncurtada(String urlEncurtada);

    Optional<Url> findByUrlOriginal(String urlOriginal);
}
