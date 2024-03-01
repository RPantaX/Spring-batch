package com.batch.service;

import com.batch.entities.PersonEntity;
import com.batch.persistence.IPersonaDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements IPersonService{
    private final IPersonaDAO personaDAO;
    @Override
    @Transactional //tenemos que importar de spring framework
    public void saveAll(List<PersonEntity> personList) {
        personaDAO.saveAll(personList);
    }
}
