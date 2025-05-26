// Chemin du fichier: src/main/java/com/example/riskmanagementapi/controller/RiskController.java
package tn.esprit.investia.controllers;

import tn.esprit.investia.entities.Risk;
import tn.esprit.investia.repository.RiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Import principal pour les annotations web

import java.util.List;
import java.util.Optional;

// Configure CORS pour autoriser les requêtes depuis le serveur de développement Angular
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/risks") // Préfixe URL pour tous les endpoints de ce contrôleur
public class RiskController {

    private final RiskRepository riskRepository;

    // Injection par constructeur
    @Autowired
    public RiskController(RiskRepository riskRepository) {
        this.riskRepository = riskRepository;
    }

    // --- Endpoint AJOUTER (POST /api/risks) ---
    @PostMapping
    public ResponseEntity<Risk> createRisk(@RequestBody Risk risk) {
        try {
            risk.setId(null); // S'assurer que c'est une création
            Risk savedRisk = riskRepository.save(risk);
            return new ResponseEntity<>(savedRisk, HttpStatus.CREATED); // Statut 201
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du risque: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Statut 500
        }
    }

    // --- Endpoint LIRE TOUS (GET /api/risks) ---
    @GetMapping
    public ResponseEntity<List<Risk>> getAllRisks() {
        try {
            List<Risk> risks = riskRepository.findAll();
            return new ResponseEntity<>(risks, HttpStatus.OK); // Statut 200
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des risques: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Statut 500
        }
    }

    // --- Endpoint LIRE UN par ID (GET /api/risks/{id}) ---
    @GetMapping("/{id}")
    public ResponseEntity<Risk> getRiskById(@PathVariable Long id) {
        try {
            Optional<Risk> riskData = riskRepository.findById(id);

            // Utilisation d'une expression lambda pour Optional (plus concis)
            return riskData.map(risk -> new ResponseEntity<>(risk, HttpStatus.OK)) // Si présent -> 200 OK
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si vide -> 404 Not Found

        } catch (Exception e) {
            System.err.println("Erreur récupération risque ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Statut 500
        }
    }

    // --- Endpoint METTRE À JOUR (PUT /api/risks/{id}) ---
    @PutMapping("/{id}")
    public ResponseEntity<Risk> updateRisk(@PathVariable Long id, @RequestBody Risk riskDetails) {
        try {
            // Utilisation de findById pour vérifier l'existence et récupérer l'entité si besoin
            return riskRepository.findById(id)
                    .map(existingRisk -> { // Si le risque existe...
                        // Vérifier la cohérence de l'ID (optionnel mais recommandé)
                        if (riskDetails.getId() != null && !riskDetails.getId().equals(id)) {
                            System.err.println("Incohérence d'ID lors de la mise à jour : URL ID=" + id + ", Body ID=" + riskDetails.getId());
                            // Retourner une ResponseEntity directement ici, mais sans exception
                            // Pour retourner une ResponseEntity depuis une lambda, il faut une approche différente
                            // ou sortir de la lambda. Restons simple pour l'instant :
                            // On ignore l'ID du body et on utilise celui de l'URL.
                        }
                        riskDetails.setId(id); // Assurer que l'ID correct est utilisé pour la mise à jour
                        Risk updatedRisk = riskRepository.save(riskDetails); // save effectue la mise à jour
                        return new ResponseEntity<>(updatedRisk, HttpStatus.OK); // Retourne 200 OK
                    })
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si findById était vide -> 404

        } catch (Exception e) {
            System.err.println("Erreur mise à jour risque ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Statut 500
        }
    }

    // --- Endpoint SUPPRIMER (DELETE /api/risks/{id}) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRisk(@PathVariable Long id) {
        try {
            if (!riskRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
            }
            riskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        } catch (Exception e) {
            System.err.println("Erreur suppression risque ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

} // Fin de la classe RiskController