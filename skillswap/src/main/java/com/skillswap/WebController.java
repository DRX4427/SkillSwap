package com.skillswap;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Redirige la racine du site vers la page de login
    @GetMapping("/") 
    public String accueil() {
        return "login"; // Cherche templates/login.html
    }

    // Affiche la page Home seulement si on est connecté
    @GetMapping("/home")
    public String home(HttpSession session) {
        // On vérifie si l'utilisateur est bien dans la session
        if (session.getAttribute("utilisateurConnecte") == null) {
            return "redirect:/"; // Sinon, retour au login
        }
        return "home"; // Cherche templates/home.html
    }
}