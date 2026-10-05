package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Paiement;

public interface PaiementRepository extends CrudRepository<Paiement, Long> {}
