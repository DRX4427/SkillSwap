package com.skillswap.controller;

import com.skillswap.model.User;
import com.skillswap.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; // Ajouté pour le logout
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        
        // 1. On cherche l'utilisateur par son email et mot de passe
        User user = userRepository.findByEmailAndPassword(email, password).orElse(null);

        System.out.println("Recherche de l'utilisateur...");
        user = userRepository.findByEmailAndPassword(email, password).orElse(null);
        if (user != null) {
            System.out.println("Utilisateur trouvé ! Nom : " + user.getNom());
        } else {
            System.out.println("Utilisateur NON trouvé dans la base.");
        }

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

    // --- NOUVELLE MÉTHODE AJOUTÉE SANS RIEN SUPPRIMER ---
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Supprime l'utilisateur de la session
        session.invalidate(); 
        
        // Ajoute un petit message pour confirmer la déconnexion
        redirectAttributes.addFlashAttribute("logoutMsg", "Vous avez été déconnecté avec succès.");
        
        return "redirect:/login";
    }
    @PostMapping("/register")


    public String register(@RequestParam String nom,
                        @RequestParam String email,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes) {

        // Vérifie si un utilisateur avec cet email existe déjà
        User existingUser = userRepository.findByEmail(email).orElse(null);

        if (existingUser != null) {
            return "redirect:/?registerError=email";
        }

        // Création du nouvel utilisateur
        User user = new User();
        user.setNom(nom);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("STUDENT");
        user.setActive(true);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("registerSuccess", "Compte créé avec succès. Vous pouvez maintenant vous connecter.");

        return "redirect:/";
    }

}