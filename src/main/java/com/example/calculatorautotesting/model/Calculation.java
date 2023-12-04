package com.example.calculatorautotesting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name="calculation")
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_1")
    private String number_1;
    @Column(name = "notation_1")
    private int notation_1;
    @NotNull
    @Column(name = "number_2")
    private String number_2;
    @Column(name = "notation_2")
    private int notation_2;
    @NotNull
    @Column(name = "operation")
    private String operation;
    @NotNull
    @Column(name = "date")
    private String date;
    @NotNull
    @Column(name = "time")
    private String time;
    @NotNull
    @Column(name = "result")
    private String result;
}
