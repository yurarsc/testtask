package com.example.testtask.dao.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
@NamedEntityGraph(name = "user_entity_graph", attributeNodes = {@NamedAttributeNode("emails"),@NamedAttributeNode("phones")})
public class User {
    @Id
    private Long id;

    private String name;
    private LocalDateTime dateOfBirth;
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<EmailData> emails;

    @OneToMany(mappedBy = "user")
    private Set<PhoneData> phones;

    @OneToOne(mappedBy = "user")
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<EmailData> getEmails() {
        return emails;
    }

    public void setEmails(Set<EmailData> emails) {
        this.emails = emails;
    }

    public Set<PhoneData> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneData> phones) {
        this.phones = phones;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
