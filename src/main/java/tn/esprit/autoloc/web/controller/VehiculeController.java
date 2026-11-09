package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules et de leurs affectations")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    @Operation(summary = "Lister les véhicules", description = "Retourne tous les véhicules.")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule trouvé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public Vehicule getVehicule(@PathVariable Long id) {
        return require(vehiculeService.retrieveVehicule(id), id);
    }

    @PostMapping
    @Operation(summary = "Créer un véhicule", description = "Crée un véhicule.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Véhicule créé"), @ApiResponse(responseCode = "400", description = "Requête invalide")})
    public ResponseEntity<Vehicule> addVehicule(@RequestBody Vehicule vehicule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.addVehicule(vehicule));
    }

    @PostMapping("/batch")
    public List<Vehicule> addVehicules(@RequestBody List<Vehicule> vehicules) {
        return vehiculeService.addVehicules(vehicules);
    }

    @PutMapping
    @Operation(summary = "Modifier un véhicule", description = "Met à jour un véhicule existant.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule modifié"), @ApiResponse(responseCode = "400", description = "Requête invalide"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public Vehicule updateVehicule(@RequestBody Vehicule vehicule) {
        if (vehicule == null || vehicule.getIdVehicule() == null) throw new IllegalArgumentException("L'identifiant du véhicule est obligatoire");
        require(vehiculeService.retrieveVehicule(vehicule.getIdVehicule()), vehicule.getIdVehicule());
        return vehiculeService.updateVehicule(vehicule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule", description = "Supprime un véhicule existant.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Véhicule supprimé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        require(vehiculeService.retrieveVehicule(id), id);
        vehiculeService.removeVehicule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/agence/{agenceId}")
    @Operation(summary = "Affecter un véhicule à une agence", description = "Rattache un véhicule à une agence.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule affecté"), @ApiResponse(responseCode = "400", description = "Affectation invalide"), @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")})
    public Vehicule affecterAgence(@PathVariable Long id, @PathVariable Long agenceId) {
        return vehiculeService.affecterVehicule(id, agenceId);
    }

    @PutMapping("/{id}/equipements/{equipementId}")
    @Operation(summary = "Affecter un équipement à un véhicule", description = "Installe un équipement sans doublon.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement affecté"), @ApiResponse(responseCode = "400", description = "Équipement déjà installé"), @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")})
    public Vehicule affecterEquipement(@PathVariable Long id, @PathVariable Long equipementId) {
        return vehiculeService.affecterEquipement(id, equipementId);
    }

    @DeleteMapping("/{id}/equipements/{equipementId}")
    @Operation(summary = "Désaffecter un équipement d'un véhicule", description = "Retire un équipement installé.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement désaffecté"), @ApiResponse(responseCode = "400", description = "Équipement non installé"), @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")})
    public Vehicule desaffecterEquipement(@PathVariable Long id, @PathVariable Long equipementId) {
        return vehiculeService.desaffecterEquipement(id, equipementId);
    }

    private Vehicule require(Vehicule vehicule, Long id) {
        if (vehicule == null) throw new EntityNotFoundException("Véhicule introuvable : " + id);
        return vehicule;
    }
}
