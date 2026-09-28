package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employe")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    private String nom;
    private String prenom;

    @Enumerated(EnumType.STRING)
    private RoleEmploye role;

    // N Employe -> 1 Agence (cote proprietaire : la cle etrangere agence_id est ici)
    // LAZY : par defaut @ManyToOne est EAGER, on le passe en LAZY pour eviter des chargements inutiles.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    private Agence agence;
}
