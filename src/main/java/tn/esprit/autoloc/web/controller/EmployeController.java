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
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;
import java.util.List;

@RestController
@RequestMapping("/employes")
@AllArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés et affectations")
public class EmployeController {
    private final IEmployeService employeService;

    @GetMapping
    @Operation(summary = "Lister les employés", description = "Retourne tous les employés.")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Employe> getAll() { return employeService.retrieveAllEmployes(); }
    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé", description = "Retourne un employé par son identifiant.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé trouvé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public Employe get(@PathVariable Long id) { return require(employeService.retrieveEmploye(id), id); }
    @PostMapping
    @Operation(summary = "Créer un employé", description = "Crée un employé.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Employé créé"), @ApiResponse(responseCode = "400", description = "Requête invalide")})
    public ResponseEntity<Employe> create(@RequestBody Employe employe) { return ResponseEntity.status(HttpStatus.CREATED).body(employeService.addEmploye(employe)); }
    @PutMapping
    @Operation(summary = "Modifier un employé", description = "Met à jour un employé existant.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé modifié"), @ApiResponse(responseCode = "400", description = "Requête invalide"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public Employe update(@RequestBody Employe employe) {
        if (employe == null || employe.getIdEmploye() == null) throw new IllegalArgumentException("L'identifiant de l'employé est obligatoire");
        require(employeService.retrieveEmploye(employe.getIdEmploye()), employe.getIdEmploye());
        return employeService.updateEmploye(employe);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un employé", description = "Supprime un employé existant.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Employé supprimé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public ResponseEntity<Void> delete(@PathVariable Long id) { require(employeService.retrieveEmploye(id), id); employeService.removeEmploye(id); return ResponseEntity.noContent().build(); }
    @PutMapping("/{id}/agence/{agenceId}")
    @Operation(summary = "Affecter un employé à une agence", description = "Affecte un employé sans autoriser un transfert direct.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé affecté"), @ApiResponse(responseCode = "400", description = "Employé déjà affecté"), @ApiResponse(responseCode = "404", description = "Employé ou agence introuvable")})
    public Employe affecter(@PathVariable Long id, @PathVariable Long agenceId) { return employeService.affecterEmploye(id, agenceId); }
    @DeleteMapping("/{id}/agence")
    @Operation(summary = "Désaffecter un employé", description = "Met l'agence de l'employé à null.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé désaffecté"), @ApiResponse(responseCode = "400", description = "Employé sans agence"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public Employe desaffecter(@PathVariable Long id) { return employeService.desaffecterEmploye(id); }
    private Employe require(Employe e, Long id) { if (e == null) throw new EntityNotFoundException("Employé introuvable : " + id); return e; }
}
