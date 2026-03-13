package com.skillswap.controller;

import com.skillswap.model.User;
import com.skillswap.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * OVERVIEW : Ce contrôleur fait office d'interface d'authentification pour l'application.
 * Il gère le cycle de vie de la session utilisateur et assure la transition 
 * entre l'état "non connecté" et "connecté" en fonction des données de la base.
 * C'est une abstraction procédurale qui encapsule la logique de vérification des rôles.
 */
@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Méthode de traitement du formulaire de connexion.
     * * @requires email != null, password != null (les paramètres sont extraits de la requête HTTP).
     * @modifies session, redirectAttributes.
     * @effects 
     * - Interroge le UserRepository pour trouver un utilisateur correspondant au couple (email, password).
     * - Si (user existe AND mot de passe correct AND compte actif) :
     * - Initialise la session avec l'attribut "utilisateurConnecte".
     * - Produit un effet de bord sur redirectAttributes avec un message de succès.
     * - Renvoie une redirection vers /admin-dashboard si le rôle est ADMIN, sinon vers /home.
     * - Si (compte trouvé mais inactif) :
     * - Renvoie une redirection vers /login avec le paramètre error=banned.
     * - Sinon (échec d'identification) :
     * - Renvoie une redirection vers /login avec le paramètre error=true.
     * @throws FailureException : Bien que non explicitée ici (procédure totale), une erreur 
     * d'accès à la DB (Runtime) pourrait survenir (Programmation défensive).
     */
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        
        // --- EXTRACTION ET LOG ---
        // Utilisation du repository pour transformer une requête SQL en objet métier User.
        User user = userRepository.findByEmailAndPassword(email, password).orElse(null);

        System.out.println("Recherche de l'utilisateur...");
        // Redondance de sécurité pour le log console (aide au débugging en cours de développement)
        user = userRepository.findByEmailAndPassword(email, password).orElse(null);
        if (user != null) {
            System.out.println("Utilisateur trouvé ! Nom : " + user.getNom());
        } else {
            System.out.println("Utilisateur NON trouvé dans la base.");
        }

        // --- LOGIQUE DE DÉCISION ---
        // Vérification de l'existence de l'objet et validité du mot de passe (sécurité applicative)
        if (user != null && user.getPassword().equals(password)) {
            
            // Vérification de l'invariant de l'utilisateur (doit être actif pour accéder au système)
            if (!user.isActive()) {
                return "redirect:/login?error=banned";
            }

            // --- ÉTABLISSEMENT DE L'ÉTAT CONNECTÉ ---
            // Mutation de l'objet session pour stocker l'état de l'utilisateur.
            session.setAttribute("utilisateurConnecte", user);
            
            // Abstraction de la notification via FlashAttributes (Thymeleaf)
            redirectAttributes.addFlashAttribute("success", "Connexion réussie ! Ravie de vous revoir, " + user.getNom());

            // Aiguillage basé sur le type d'utilisateur (Abstraction de la hiérarchie de rôles)
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin-dashboard"; 
            } else {
                return "redirect:/home"; 
            }
        }

        // Échec de la post-condition de succès : retour à l'état initial avec signalement d'erreur
        return "redirect:/login?error=true";
    }

    /**
     * Méthode de clôture de session.
     * * @requires session != null (une session doit exister pour être invalidée).
     * @modifies session, redirectAttributes.
     * @effects 
     * - Appelle session.invalidate() pour supprimer toutes les données de l'état courant.
     * - Ajoute un message de confirmation à redirectAttributes pour informer l'utilisateur.
     * - Renvoie une redirection vers la page de login.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalidation de la représentation de la session
        session.invalidate(); 
        
        // Effet de bord pour le feedback utilisateur
        redirectAttributes.addFlashAttribute("logoutMsg", "Vous avez été déconnecté avec succès.");
        
        return "redirect:/login";
    }
}