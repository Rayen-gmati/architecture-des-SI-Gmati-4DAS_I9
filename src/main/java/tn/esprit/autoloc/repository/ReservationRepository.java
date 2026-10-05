package tn.esprit.autoloc.repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {}
