package com.example.testtask.api;

import com.example.testtask.dao.PhoneData;

public record PhoneResponse(Long id, String phone) {
    public PhoneResponse(PhoneData phoneData) {
        this(phoneData.getId(), phoneData.getPhone());
    }
}
