package com.example.testtask.api.data;

import com.example.testtask.dao.entities.PhoneData;

public record PhoneResponse(Long id, String phone) {
    public PhoneResponse(PhoneData phoneData) {
        this(phoneData.getId(), phoneData.getPhone());
    }
}
