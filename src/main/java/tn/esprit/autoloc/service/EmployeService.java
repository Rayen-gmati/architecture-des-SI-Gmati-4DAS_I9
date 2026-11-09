package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EmployeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeService implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;

    @Override
    public List<Employe> retrieveAllEmployes() {
        return (List<Employe>) employeRepository.findAll();
    }

    @Override
    public Employe addEmploye(Employe e) {
        return employeRepository.save(e);
    }

    @Override
    public Employe updateEmploye(Employe e) {
        return employeRepository.save(e);
    }

    @Override
    public Employe retrieveEmploye(Long idEmploye) {
        return employeRepository.findById(idEmploye).orElse(null);
    }

    @Override
    public void removeEmploye(Long idEmploye) {
        employeRepository.deleteById(idEmploye);
    }

    @Override
    public List<Employe> addEmployes(List<Employe> employes) {
        return (List<Employe>) employeRepository.saveAll(employes);
    }

    @Override
    @Transactional
    public Employe affecterEmploye(Long idEmploye, Long idAgence) {
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable : " + idEmploye));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : " + idAgence));
        if (employe.getAgence() != null) {
            throw new IllegalStateException("L'employé est déjà affecté à une agence");
        }
        employe.setAgence(agence);
        agence.getEmployes().add(employe);
        return employeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employe desaffecterEmploye(Long idEmploye) {
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable : " + idEmploye));
        if (employe.getAgence() == null) {
            throw new IllegalStateException("L'employé n'est affecté à aucune agence");
        }
        employe.getAgence().getEmployes().remove(employe);
        employe.setAgence(null);
        return employeRepository.save(employe);
    }
}
