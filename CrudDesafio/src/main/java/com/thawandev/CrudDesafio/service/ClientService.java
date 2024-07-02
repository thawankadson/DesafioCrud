package com.thawandev.CrudDesafio.service;

import com.thawandev.CrudDesafio.dto.ClientDTO;
import com.thawandev.CrudDesafio.entities.Client;
import com.thawandev.CrudDesafio.repositories.ClientRepository;
import com.thawandev.CrudDesafio.service.execption.ResourceNotFoundExecption;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        return new ClientDTO(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundExecption("Recurso não encontrado")));
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(ClientDTO::new);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        passDtoToEntity(dto, entity);
        return new ClientDTO(repository.save(entity));
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            passDtoToEntity(dto, entity);
            return new ClientDTO(repository.save(entity));
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExecption("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundExecption("Recurso não encontrado");
        }
        else {
            repository.deleteById(id);
        }
    }

    private void passDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}

