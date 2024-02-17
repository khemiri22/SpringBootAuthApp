package com.example.SpringAPI.services.produit;

import com.example.SpringAPI.models.Produit;

import java.util.List;

public interface IProduitService {
    Produit create(Produit produit);
    List<Produit> read();
    Produit update(Long id,Produit produit);
    Produit delete(Long id);



}
