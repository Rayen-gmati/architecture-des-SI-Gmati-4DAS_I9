package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;

    // 1 Contrat -> 1 Reservation (cote proprietaire : cle etrangere reservation_id ici,
    // car le contrat est cree apres la reservation, a sa validation)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // 1 Contrat -> N Paiement
    // cascade = ALL : les paiements suivent le cycle de vie du contrat
    // (suppression d'un contrat => suppression de ses paiements).
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Paiement> paiements = new ArrayList<>();
}
