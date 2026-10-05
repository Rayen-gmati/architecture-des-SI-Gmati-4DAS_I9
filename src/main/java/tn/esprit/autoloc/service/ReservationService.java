package tn.esprit.autoloc.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.ReservationRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> retrieveAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    @Override
    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation retrieveReservation(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public void removeReservation(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }

    @Override
    public List<Reservation> addReservations(List<Reservation> reservations) {
        return (List<Reservation>) reservationRepository.saveAll(reservations);
    }
}
