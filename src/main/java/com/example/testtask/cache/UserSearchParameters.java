package com.example.testtask.cache;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record UserSearchParameters(LocalDateTime dateOfBirth,
                                   String phone,
                                   String name,
                                   String email,
                                   Pageable pageable) implements Comparable<UserSearchParameters>{
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserSearchParameters that = (UserSearchParameters) o;
        return Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(pageable, that.pageable) && Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfBirth, phone, name, email, pageable);
    }

    @Override
    public int compareTo(UserSearchParameters o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String totalString = "%s%s%s%s%s".formatted(dateOfBirth==null ? null : dateOfBirth.format(formatter), phone, name, email, pageable.toString());
        String totalOString = "%s%s%s%s%s".formatted(o.dateOfBirth==null ? null : o.dateOfBirth.format(formatter), o.phone, o.name, o.email, o.pageable.toString());
        return totalString.compareTo(totalOString);
    }

}
