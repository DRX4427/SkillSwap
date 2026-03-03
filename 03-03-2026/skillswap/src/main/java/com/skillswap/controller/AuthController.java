package com.skillswap.controller;

import com.skillswap.model.User;
import com.skillswap.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Indispensable pour les messages

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        HttpSession session,
                        RedirectAttributes redirectAttributes) { // Ajouté pour les notifications
        
        // 1. On cherche l'utilisateur par son email et mot de passe
        // Note : On cherche par email et mot de passe pour mieux gérer les messages d'erreur après
        User user = userRepository.findByEmailAndPassword(email, password).orElse(null);

        // 2. Vérification de l'existence et du mot de passe
        if (user != null && user.getPassword().equals(password)) {
            
            // 3. Vérification de la modération (ton champ is_active)
            if (!user.isActive()) {
                return "redirect:/login?error=banned";
            }

            // 4. Succès : On garde l'utilisateur en mémoire (Session)
            session.setAttribute("utilisateurConnecte", user);
            
            // On prépare le message de succès qui s'affichera sur la page suivante
            redirectAttributes.addFlashAttribute("success", "Connexion réussie ! Ravie de vous revoir, " + user.getNom());

            // 5. Redirection selon le ROLE
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin-dashboard"; 
            } else {
                return "redirect:/home"; 
            }
        }

        // Si l'email n'existe pas ou le mot de passe est faux
        return "redirect:/login?error=true";
    }
}