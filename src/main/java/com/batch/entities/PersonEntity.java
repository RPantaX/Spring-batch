package com.batch.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name = "persons")
public class PersonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private int age;
    @Column(name = "insertion_date")
    private String insertionDate;
}
