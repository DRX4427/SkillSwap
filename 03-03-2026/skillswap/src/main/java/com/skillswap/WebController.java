package com.skillswap;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * OVERVIEW : 
 * WebController agit comme le routeur principal de l'application. 
 * Il définit les points d'entrée (endpoints) pour la navigation de base 
 * et assure la protection des vues en vérifiant l'état de la session utilisateur.
 * C'est une abstraction procédurale qui masque la complexité de la gestion 
 * des fichiers HTML (templates) derrière des routes logiques.
 */
@Controller
public class WebController {

    /**
     * Point d'entrée racine de l'application.
     * * @requires aucune (accessible par tout utilisateur, même non connecté).
     * @modifies rien.
     * @effects Renvoie le nom logique de la vue "login" qui correspond au fichier templates/login.html.
     */
    @GetMapping("/") 
    public String accueil() {
        // Redirige par défaut vers la page de login pour inviter l'utilisateur à s'identifier.
        return "login"; 
    }

    /**
     * Gère l'accès à la page d'accueil principale du site.
     * * @requires session != null (fournie par le conteneur de servlets).
     * @modifies rien (lecture seule de la session).
     * @effects 
     * - Vérifie si l'invariant de session "utilisateurConnecte" est présent.
     * - Si (session contient "utilisateurConnecte") :
     * Renvoie le nom de la vue "home" (accès autorisé).
     * - Sinon :
     * Renvoie une instruction de redirection vers "/" (protection de route).
     * * @throws FailureException (non vérifiée) si le moteur de template Thymeleaf 
     * ne parvient pas à localiser le fichier home.html.
     */
    @GetMapping("/home")
    public String home(HttpSession session) {
        // --- CONTRÔLE D'ACCÈS (Programmation défensive) ---
        // On vérifie si l'utilisateur est bien dans la session (authentifié via AuthController)
        if (session.getAttribute("utilisateurConnecte") == null) {
            // Si la session est vide, on empêche l'accès et on renvoie à la racine.
            return "redirect:/"; 
        }
        
        // Si l'utilisateur est présent, on affiche le template home.html.
        return "home"; 
    }
}