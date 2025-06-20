package com.example.testtask.dao;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "phone_data", schema = "public")
public class PhoneData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String phone;

    public PhoneData() {
    }

    public PhoneData(User user, String phone) {
        this.user = user;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
