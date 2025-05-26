// Chemin du fichier: src/main/java/com/example/riskmanagementapi/domain/Risk.java
package tn.esprit.investia.entities; // <<< VOTRE NOM DE PACKAGE CORRECTEMENT UTILISÉ

import jakarta.persistence.*; // JPA annotations (ou javax.persistence.* pour Spring Boot 2.x)
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "risks") // Nom de la table en base de données
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément géré par MySQL
    private Long id;

    @Column(nullable = false, length = 255) // Colonne 'name', non nulle, max 255 chars
    private String name;

    @Lob // Pour les textes longs
    @Column(nullable = false, columnDefinition = "TEXT") // Non nulle, type TEXT
    private String description;

    @Column(nullable = false, length = 100) // Colonne 'category', non nulle, max 100 chars
    private String category;

    @Column(nullable = false) // Colonne 'impact', non nulle
    private Integer impact; // Note: Utiliser Integer vs int permet null si nullable=true

    @Column(nullable = false) // Colonne 'probability', non nulle
    private Integer probability;

    @Column(length = 255) // Colonne 'owner', peut être nulle, max 255 chars
    private String owner;

    @Lob // Pour les textes longs
    @Column(columnDefinition = "TEXT") // Colonne 'mitigation_plan', peut être nulle, type TEXT
    private String mitigationPlan;

    // --- Constructeurs, Getters, Setters, toString, equals, hashCode ---
    // --- sont générés automatiquement par Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor) ---

    // --- Commentaires sur les champs optionnels (dates, etc.) restent valides ---
    // @Column(name = "created_at", updatable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    // private java.util.Date createdAt;
    // ...etc
}