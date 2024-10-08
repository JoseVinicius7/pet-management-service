package br.com.app.pet_management_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@Table(name = "tbg_pet")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PetEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "breed")
    private String breed;
    @Column(name = "age")
    private Integer age;
    @Column(name = "additionalInfo")
    private String additionalInfo;

}
