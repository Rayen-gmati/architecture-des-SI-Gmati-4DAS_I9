package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import java.util.List;

@RestController
@RequestMapping("/agences")
@AllArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {
    private final IAgenceService agenceService;

    @GetMapping
    @Operation(summary = "Lister les agences", description = "Retourne toutes les agences.")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Agence> getAll() { return agenceService.retrieveAllAgences(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une agence", description = "Retourne une agence par son identifiant.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Agence trouvée"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    public Agence get(@PathVariable Long id) { return require(agenceService.retrieveAgence(id), id); }

    @PostMapping
    @Operation(summary = "Créer une agence", description = "Crée une agence.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Agence créée"), @ApiResponse(responseCode = "400", description = "Requête invalide")})
    public ResponseEntity<Agence> create(@RequestBody Agence agence) { return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.addAgence(agence)); }

    @PutMapping
    @Operation(summary = "Modifier une agence", description = "Met à jour une agence existante.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Agence modifiée"), @ApiResponse(responseCode = "400", description = "Requête invalide"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    public Agence update(@RequestBody Agence agence) {
        if (agence == null || agence.getIdAgence() == null) throw new IllegalArgumentException("L'identifiant de l'agence est obligatoire");
        require(agenceService.retrieveAgence(agence.getIdAgence()), agence.getIdAgence());
        return agenceService.updateAgence(agence);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une agence", description = "Supprime une agence existante.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Agence supprimée"), @ApiResponse(responseCode = "404", description = "Agence introuvable")})
    public ResponseEntity<Void> delete(@PathVariable Long id) { require(agenceService.retrieveAgence(id), id); agenceService.removeAgence(id); return ResponseEntity.noContent().build(); }

    private Agence require(Agence agence, Long id) {
        if (agence == null) throw new EntityNotFoundException("Agence introuvable : " + id);
        return agence;
    }
}
