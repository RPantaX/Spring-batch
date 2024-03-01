package com.batch.persistence;

import com.batch.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonaDAO  extends JpaRepository<PersonEntity, Long> {
}
