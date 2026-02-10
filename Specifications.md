# Spécification du Projet : SkillSwap (INFOB238)

## 1. Présentation Générale
* **Nom du projet** : SkillSwap.
* **Finalité** : Plateforme d'entraide permettant aux étudiants d'échanger des services (tutorat, aide technique, prêt de matériel) sans transaction monétaire.
* **Public visé** : La communauté estudiantine universitaire.

---

## 2. Fonctionnalités Principales
* **Système d'Authentification** : Inscription et connexion sécurisées obligatoires pour accéder aux fonctionnalités de l'application.
* **Gestion des Rôles** : Distinction stricte entre minimum deux types de comptes :
    * **Étudiants (User)** : Peuvent poster des annonces, répondre à des offres et gérer leur profil.
    * **Administrateurs (Admin)** : Disposent de droits de modération, peuvent bannir des utilisateurs et gérer les catégories de services.
* **Gestion des Annonces** : Création, modification et suppression des offres de services.
* **Moteur de Recherche** : Filtrage dynamique des services par catégorie via l'API.

---

## 3. Architecture Technique
* **Frontend** : HTML5 sémantique, CSS3 (priorité aux transitions CSS pour les effets visuels) et JavaScript.
* **Backend** : Application Java structurée de manière professionnelle (modèles, contrôleurs, services).
* **Base de Données** : Stockage persistant des utilisateurs, des annonces et des interactions.
* **Points d'API JSON** :
    * `GET /api/services` : Récupération de la liste des services pour le frontend.
    * `POST /api/request` : Soumission d'une nouvelle demande de mise en relation.
    * `PUT /api/user/status` : Mise à jour en temps réel des informations utilisateur ou de l'état d'un service.
* **Middleware** : Couche de sécurité vérifiant les autorisations avant l'accès aux routes sensibles (ex: panel d'administration).

---

## 4. Arborescence des Pages
1. **Accueil** : Présentation du concept et affichage des dernières annonces.
2. **Connexion / Inscription** : Formulaires de gestion d'accès sécurisés.
3. **Tableau de bord Utilisateur** : Vue d'ensemble des annonces créées et gestion du profil personnel.
4. **Interface Admin** : Outils de modération et gestion globale de la plateforme (accès restreint).

---

## 5. Répartition du Travail (Équipe de 4)

| Membre | Responsabilité Principale | Focus Évaluation (Grille) |
| :--- | :--- | :--- |
| **Étudiant 1** | **Backend & API** | Structure Java, implémentation du Middleware et des routes JSON. |
| **Étudiant 2** | **Frontend & UI/UX** | Structure HTML/CSS sémantique et effets visuels gérés en CSS. |
| **Étudiant 3** | **Sécurité & BDD** | Prévention XSS/CSRF/SQLi et validation des données transmises. |
| **Étudiant 4** | **Documentation & QA** | Guide d'installation (Readme.md), rapport final et tests d'intégration. |

---

## 6. Engagements Qualité & Sécurité
* **Sécurité** : Application rigoureuse des mesures contre les failles XSS, CSRF et les injections SQL. Validation systématique des données côté serveur.
* **Qualité du Code** : Respect des bonnes pratiques de nommage, indentation stricte et modularité du code (Java, JS, CSS).
* **Maîtrise du Code** : Utilisation transparente de l'IA ; chaque membre assure une compréhension totale de son code pour les modifications en direct lors de la défense orale.
