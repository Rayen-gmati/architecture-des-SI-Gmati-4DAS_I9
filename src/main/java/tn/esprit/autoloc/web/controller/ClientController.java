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
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;
import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {

    private final IClientService clientService;

    @GetMapping
    @Operation(summary = "Lister les clients", description = "Retourne tous les clients.")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    public List<Client> getAllClients() {
        return clientService.retrieveAllClients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un client", description = "Retourne un client par son identifiant.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Client trouvé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
    public Client getClient(@PathVariable Long id) {
        return require(clientService.retrieveClient(id), "Client", id);
    }

    @PostMapping
    @Operation(summary = "Créer un client", description = "Crée un client.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Client créé"), @ApiResponse(responseCode = "400", description = "Requête invalide")})
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(client));
    }

    @PostMapping("/batch")
    public List<Client> addClients(@RequestBody List<Client> clients) {
        return clientService.addClients(clients);
    }

    @PutMapping
    @Operation(summary = "Modifier un client", description = "Met à jour un client existant.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Client modifié"), @ApiResponse(responseCode = "400", description = "Requête invalide"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
    public Client updateClient(@RequestBody Client client) {
        if (client == null || client.getIdClient() == null) throw new IllegalArgumentException("L'identifiant du client est obligatoire");
        require(clientService.retrieveClient(client.getIdClient()), "Client", client.getIdClient());
        return clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client", description = "Supprime un client existant.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Client supprimé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        require(clientService.retrieveClient(id), "Client", id);
        clientService.removeClient(id);
        return ResponseEntity.noContent().build();
    }

    private Client require(Client client, String type, Long id) {
        if (client == null) throw new EntityNotFoundException(type + " introuvable : " + id);
        return client;
    }
}