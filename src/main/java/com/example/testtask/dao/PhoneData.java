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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhoneData phoneData = (PhoneData) o;
        return Objects.equals(id, phoneData.id)
                && Objects.equals(user.getId(), phoneData.user.getId())
                && Objects.equals(phone, phoneData.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user.getId(), phone);
    }

    @Override
    public String toString() {
        return "PhoneData{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", phone='" + phone + '\'' +
                '}';
    }
}
