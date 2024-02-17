package com.example.SpringAPI.repositories;

import com.example.SpringAPI.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProduitRepository extends JpaRepository<Produit,Long> {

}
