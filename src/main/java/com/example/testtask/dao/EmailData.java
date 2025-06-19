package com.example.testtask.dao;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "email_data", schema = "public")
public class EmailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String email;

    public EmailData() {
    }

    public EmailData(User user, String email) {
        this.user = user;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmailData emailData = (EmailData) o;
        return Objects.equals(id, emailData.id)
                && Objects.equals(user.getId(), emailData.user.getId())
                && Objects.equals(email, emailData.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user.getId(), email);
    }

    @Override
    public String toString() {
        return "EmailData{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", email='" + email + '\'' +
                '}';
    }
}
