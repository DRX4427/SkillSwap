package com.skillswap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OVERVIEW : 
 * SkillswapApplication est la classe racine et le point d'entrée unique du système. 
 * Elle agit comme une abstraction de haut niveau qui orchestre le démarrage 
 * de tous les composants de l'application (serveur, base de données, contrôleurs).
 * * L'annotation @SpringBootApplication est une "méta-abstraction" qui active :
 * 1. L'auto-configuration (choix des meilleurs réglages par défaut).
 * 2. Le scan des composants (détection automatique de vos classes AuthController, User, etc.).
 */
@SpringBootApplication
public class SkillswapApplication {

    /**
     * Méthode statique de démarrage du programme.
     * * @requires args != null (tableau d'arguments fourni par la machine virtuelle Java).
     * @modifies L'état global de la JVM et initialise le "ApplicationContext" de Spring.
     * @effects 
     * - Analyse les annotations du projet pour construire la hiérarchie d'objets.
     * - Établit la connexion avec la base de données MySQL via Hibernate.
     * - Démarre le serveur Tomcat embarqué pour écouter les requêtes HTTP sur le port configuré.
     * - Rend l'application opérationnelle et prête à recevoir des connexions.
     * * @throws FailureException (Exception non vérifiée, Chapitre 4) si une erreur critique 
     * survient au lancement (ex: Port 8080 déjà utilisé ou URL de base de données invalide).
     */
	public static void main(String[] args) {
        // Cette ligne déclenche l'abstraction de l'initialisation du framework.
		SpringApplication.run(SkillswapApplication.class, args);
	}

}