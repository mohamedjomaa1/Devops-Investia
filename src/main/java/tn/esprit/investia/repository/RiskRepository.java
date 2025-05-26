// Chemin du fichier: src/main/java/com/example/riskmanagementapi/repository/RiskRepository.java
package tn.esprit.investia.repository; // <<< VOTRE NOM DE PACKAGE POUR LES REPOSITORIES

import tn.esprit.investia.entities.Risk; // Importer l'entité Risk
import org.springframework.data.jpa.repository.JpaRepository; // Importer JpaRepository
import org.springframework.stereotype.Repository; // (Optionnel mais bonne pratique) Indique que c'est un bean de repository

@Repository // (1) Indique à Spring que c'est un composant Repository (utile pour l'injection et la traduction d'exceptions)
public interface RiskRepository extends JpaRepository<Risk, Long> { // (2) Hérite de JpaRepository

    // === C'EST TOUT POUR L'INSTANT ! ===

    // (3) Spring Data JPA va automatiquement fournir les implémentations pour des méthodes comme :
    // - save(Risk entity): Sauvegarde ou met à jour une entité. Retourne l'entité sauvegardée.
    // - findById(Long id): Trouve une entité par son ID. Retourne un Optional<Risk>.
    // - findAll(): Trouve toutes les entités. Retourne une List<Risk>.
    // - deleteById(Long id): Supprime une entité par son ID.
    // - count(): Compte le nombre total d'entités.
    // - existsById(Long id): Vérifie si une entité existe par son ID.
    // - ... et bien d'autres !

    // (4) On peut aussi ajouter des méthodes personnalisées en suivant des conventions de nommage.
    // Par exemple, pour trouver tous les risques d'une certaine catégorie :
    // List<Risk> findByCategory(String category);
    // Spring Data JPA générera la requête correspondante automatiquement !
    // (Nous n'en avons pas besoin pour l'instant, mais c'est bon à savoir).

}