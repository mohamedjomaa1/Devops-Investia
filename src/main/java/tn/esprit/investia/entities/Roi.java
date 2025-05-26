package tn.esprit.investia.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Roi {
    private double percentage;
    private int times;
}
