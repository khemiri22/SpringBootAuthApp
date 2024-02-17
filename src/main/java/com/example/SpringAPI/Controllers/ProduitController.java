package com.example.SpringAPI.Controllers;

import com.example.SpringAPI.models.Produit;
import com.example.SpringAPI.services.produit.ProduitService;
import com.example.SpringAPI.utilities.CookiesManager;
import com.example.SpringAPI.utilities.JwtManager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produit")
@AllArgsConstructor
public class ProduitController {
    private final ProduitService produitService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Produit produit,@CookieValue(name = "jwt", required = false)String cookieValue) {
        String token = CookiesManager.getCookie(cookieValue);
        if (token != null) {
            String uid = JwtManager.validateJwtToken(token);
            if (uid != null) {
                Produit p = produitService.create(produit);
                return ResponseEntity.ok(p);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");

    }

    @GetMapping
    public ResponseEntity<Object> read(@CookieValue(name = "jwt", required = false)String cookieValue) {
        String token = CookiesManager.getCookie(cookieValue);
        if (token != null) {
            String uid = JwtManager.validateJwtToken(token);
            if (uid != null) {
                List<Produit> produits = produitService.read();
                return ResponseEntity.ok(produits);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Produit produit,@CookieValue(name = "jwt", required = false)String cookieValue) {
        String token = CookiesManager.getCookie(cookieValue);
        if (token != null) {
            String uid = JwtManager.validateJwtToken(token);
            if (uid != null) {
                Produit p = produitService.update(id, produit);
                if (p != null) {
                    return ResponseEntity.ok(p);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,@CookieValue(name = "jwt", required = false)String cookieValue) {
        String token = CookiesManager.getCookie(cookieValue);
        if (token != null) {
            String uid = JwtManager.validateJwtToken(token);
            if (uid != null) {
                Produit p = produitService.delete(id);
                if (p != null) {
                    return ResponseEntity.ok(p);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied !");
    }
}
