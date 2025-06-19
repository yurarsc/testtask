package com.example.testtask.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(name, user.name)
                && Objects.equals(dateOfBirth, user.dateOfBirth)
                && Objects.equals(password, user.password)
                && Objects.equals(emailsAsStrings(), user.emailsAsStrings())
                && Objects.equals(phonesAsStrings(), user.phonesAsStrings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, password, emailsAsStrings(), phonesAsStrings());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", password='" + password + '\'' +
                ", emails=" + emailsAsStrings() +
                ", phones=" + phonesAsStrings() +
                '}';
    }
}
