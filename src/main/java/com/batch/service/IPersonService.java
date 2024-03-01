package com.batch.service;

import com.batch.entities.PersonEntity;

import java.util.List;

public interface IPersonService {
    void saveAll(List<PersonEntity> personList);
}
