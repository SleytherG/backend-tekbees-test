package com.tekbees.services;

import com.tekbees.dto.MensajeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseService<T> {

    public MensajeDTO save(T object);
    public MensajeDTO update(T object);
    public List<T> findAll();
    public T findById(int id);
}
