package com.example.SpringAPI.services.produit;

import com.example.SpringAPI.models.Produit;
import com.example.SpringAPI.repositories.IProduitRepository;
import com.example.SpringAPI.services.produit.IProduitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProduitService implements IProduitService {

    private final IProduitRepository iProduitRepository;
    @Override
    public Produit create(Produit produit) {
        return iProduitRepository.save(produit);
    }

    @Override
    public List<Produit> read() {
        return iProduitRepository.findAll();
    }

    @Override
    public Produit update(Long id, Produit produit) {
            return iProduitRepository.findById(id).map(p ->{
                p.setNom(produit.getNom());
                p.setDescription(produit.getDescription());
                p.setPrix(produit.getPrix());
                return iProduitRepository.save(p);
            }).orElse(null);
    }

    @Override
    public Produit delete(Long id) {
        Optional<Produit> existingProduitOptional = iProduitRepository.findById(id);
        if(existingProduitOptional.isPresent()){
            Produit existingProduit = existingProduitOptional.get();
            iProduitRepository.deleteById(id);
            return existingProduit;
        }
        return null;
    }
}
