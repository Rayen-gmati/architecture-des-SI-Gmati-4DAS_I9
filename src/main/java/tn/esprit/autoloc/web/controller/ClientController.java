package tn.esprit.autoloc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;
import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.retrieveAllClients();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.retrieveClient(id);
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PostMapping("/batch")
    public List<Client> addClients(@RequestBody List<Client> clients) {
        return clientService.addClients(clients);
    }

    @PutMapping
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.removeClient(id);
    }
}
