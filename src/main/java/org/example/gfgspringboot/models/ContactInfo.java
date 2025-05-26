package org.example.gfgspringboot.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ContactInfo implements Serializable {
    private String email;
    private String phone;
}
