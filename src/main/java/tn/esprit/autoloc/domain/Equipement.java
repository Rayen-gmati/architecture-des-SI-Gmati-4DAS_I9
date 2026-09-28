package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipement")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    private String libelle;

    // Cote inverse du ManyToMany : mappedBy designe l'attribut "equipements" de Vehicule.
    // Pas de cascade : supprimer un equipement ne doit pas supprimer les vehicules.
    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Vehicule> vehicules = new HashSet<>();
}
