package com.thawandev.CrudDesafio.repositories;

import com.thawandev.CrudDesafio.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
