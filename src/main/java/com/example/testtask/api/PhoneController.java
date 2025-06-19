package com.example.testtask.api;

import com.example.testtask.auth.JWTHelper;
import com.example.testtask.dao.PhoneData;
import com.example.testtask.service.PhoneService;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phones")
public class PhoneController {
    private PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PhoneResponse create(@RequestBody PhoneRequest request,
                                JwtAuthenticationToken jwtToken) {
        Long userId = JWTHelper.extractUserId(jwtToken);
        PhoneData phoneData = phoneService.create(userId, request.phone());
        return new PhoneResponse(phoneData);
    }

    @PutMapping("/{id}")
    public PhoneResponse update(@PathVariable("id") Long phoneId,
                                @RequestBody PhoneRequest request,
                                JwtAuthenticationToken jwtToken) {
        Long userId = JWTHelper.extractUserId(jwtToken);
        PhoneData phoneData = phoneService.update(userId, phoneId, request.phone());
        return new PhoneResponse(phoneData);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long phoneId,
                       JwtAuthenticationToken jwtToken) {
        Long userId = JWTHelper.extractUserId(jwtToken);
        phoneService.delete(userId, phoneId);
    }
}
