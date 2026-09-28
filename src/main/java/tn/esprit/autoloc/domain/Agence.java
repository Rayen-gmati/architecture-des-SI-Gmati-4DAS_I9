package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    // 1 Agence -> N Vehicule
    // LAZY : on ne charge la flotte que si on en a besoin.
    // Pas de cascade de suppression : supprimer une agence ne supprime pas ses vehicules.
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Vehicule> vehicules = new ArrayList<>();

    // 1 Agence -> N Employe
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Employe> employes = new ArrayList<>();
}
