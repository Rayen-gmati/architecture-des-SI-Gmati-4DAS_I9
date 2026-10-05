package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.EmployeRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeService implements IEmployeService {

    private final EmployeRepository employeRepository;

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
}
