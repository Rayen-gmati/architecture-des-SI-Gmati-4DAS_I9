package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.ClientRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> retrieveAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client addClient(Client c) {
        return clientRepository.save(c);
    }

    @Override
    public Client updateClient(Client c) {
        return clientRepository.save(c);
    }

    @Override
    public Client retrieveClient(Long idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public void removeClient(Long idClient) {
        clientRepository.deleteById(idClient);
    }

    @Override
    public List<Client> addClients(List<Client> clients) {
        return (List<Client>) clientRepository.saveAll(clients);
    }
}
