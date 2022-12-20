package io.github.rura6502.start_batch.core.repository;

import io.github.rura6502.start_batch.core.domain.PlainText;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainTextRepository extends JpaRepository<PlainText, Long> {

  Page<PlainText> findBy(Pageable pageable);
}
