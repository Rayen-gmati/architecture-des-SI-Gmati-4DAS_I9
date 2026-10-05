package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.AgenceRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class AgenceService implements IAgenceService {

    private final AgenceRepository agenceRepository;

    @Override
    public List<Agence> retrieveAllAgences() {
        return (List<Agence>) agenceRepository.findAll();
    }

    @Override
    public Agence addAgence(Agence a) {
        return agenceRepository.save(a);
    }

    @Override
    public Agence updateAgence(Agence a) {
        return agenceRepository.save(a);
    }

    @Override
    public Agence retrieveAgence(Long idAgence) {
        return agenceRepository.findById(idAgence).orElse(null);
    }

    @Override
    public void removeAgence(Long idAgence) {
        agenceRepository.deleteById(idAgence);
    }

    @Override
    public List<Agence> addAgences(List<Agence> agences) {
        return (List<Agence>) agenceRepository.saveAll(agences);
    }
}
