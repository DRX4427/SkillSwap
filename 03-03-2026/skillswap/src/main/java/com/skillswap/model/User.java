package com.skillswap.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * OVERVIEW : 
 * User est une classe d'abstraction de données (ADT) mutable représentant un membre 
 * de la plateforme SkillSwap. Un utilisateur possède une identité unique (id), 
 * des informations de profil (nom, email), des informations de sécurité (password) 
 * et des permissions spécifiques (role).
 * * MUTABILITÉ : 
 * Cette classe est MUTABLE car elle fournit des setters permettant de modifier 
 * l'état de l'utilisateur après sa création.
 * * SPECFIELDS : 
 * id : Long // Identifiant unique en base de données
 * nom : String // Nom complet de l'utilisateur
 * email : String // Adresse de contact et identifiant de connexion
 * password : String // Mot de passe (stocké en clair pour le prototype)
 * role : String // Catégorie d'utilisateur ('STUDENT' ou 'ADMIN')
 * isActive : boolean // État du compte (actif ou banni)
 * * INVARIANT DE REPRÉSENTATION (IR) : 
 * - email != null && email contient '@'
 * - nom != null && nom != ""
 * - role != null && (role == "STUDENT" || role == "ADMIN")
 * - password != null && password.length() >= 8 (règle métier souhaitée)
 * * FONCTION D'ABSTRACTION (FA) : 
 * FA(r) = un utilisateur de SkillSwap tel que :
 * son identité = r.id
 * son pseudo = r.nom
 * sa clé d'accès = r.password
 * ses privilèges = r.role
 * son statut d'accès = r.isActive
 */
@Entity
@Table(name = "user") // Fait le lien avec ta table SQL 'user'
@Getter @Setter // Lombok génère automatiquement les Getters et Setters à la compilation
public class User {

    /**
     * @requires id > 0 (si l'utilisateur est déjà persisté en base)
     * @effects Identifiant unique auto-incrémenté par MySQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @requires nom != null
     */
    private String nom;

    /**
     * @requires email != null && format valide
     */
    private String email;

    /**
     * @requires password != null
     * @effects Stocke le secret permettant l'authentification
     */
    private String password;

    /**
     * @requires role == "STUDENT" || role == "ADMIN"
     * @effects Définit les permissions d'accès aux routes du WebController
     */
    private String role; // 'STUDENT' ou 'ADMIN'

    /**
     * @effects 
     * true : l'utilisateur peut se connecter
     * false : le AuthController redirigera vers ?error=banned
     */
    @Column(name = "is_active") // Mappe le champ Java 'isActive' à la colonne SQL 'is_active'
    private boolean isActive;
}