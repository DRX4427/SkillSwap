package com.skillswap.repository;

import com.skillswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * OVERVIEW : 
 * UserRepository est une interface définissant une abstraction de collection 
 * persistante pour les entités User. Elle sert de médiateur entre la couche 
 * de service (Java) et la couche de données (SQL MySQL).
 * * En étendant JpaRepository, elle hérite d'un comportement standard pour 
 * le stockage, la mise à jour et la suppression d'objets User.
 * * SPECFIELDS :
 * users : Set<User> // Représente l'ensemble des utilisateurs présents en base de données.
 * * ABSTRACTION :
 * Cette interface cache les détails de l'implémentation SQL (le "Comment") 
 * pour ne laisser apparaître que les capacités de recherche (le "Quoi").
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche dichotomique (abstraite) d'un utilisateur par ses identifiants.
     * * @requires email != null && password != null.
     * @modifies rien (C'est une méthode d'observation pure, pas d'effet de bord sur la DB).
     * @effects 
     * - Déclenche une requête SQL de type SELECT.
     * - Si un enregistrement correspond exactement à l'email ET au password :
     * Renvoie un Optional contenant l'instance de l'User correspondante.
     * - Sinon :
     * Renvoie un Optional vide (Optional.empty()).
     * * @throws FailureException si une erreur de communication survient avec le serveur MySQL 
     * (Situation exceptionnelle de niveau système, Chapitre 4).
     */
    Optional<User> findByEmailAndPassword(String email, String password);
}