package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Maintenance;

public interface MaintenanceRepository extends CrudRepository<Maintenance, Long> {}
