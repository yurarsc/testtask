package com.example.testtask.dao;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    private Long id;

    private String name;
    private LocalDate dateOfBirth;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<EmailData> emails;

    @OneToMany(mappedBy = "user")
    private List<PhoneData> phones;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<EmailData> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailData> emails) {
        this.emails = emails;
    }

    public List<PhoneData> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneData> phones) {
        this.phones = phones;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<String> emailsAsStrings() {
        return getEmails().stream()
                .map(EmailData::getEmail)
                .toList();
    }

    public List<String> phonesAsStrings() {
        return getPhones().stream()
                .map(PhoneData::getPhone)
                .toList();

    }
}
