package com.example.testtask.service;

import com.example.testtask.dao.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhoneService {
    private PhoneRepository phoneRepository;
    private UserRepository userRepository;

    public PhoneService(PhoneRepository phoneRepository,
                        UserRepository userRepository) {
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
    }

    public PhoneData create(Long userId, String phone) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        User user = optUser.get();
        PhoneData entity = new PhoneData(user, phone);
        return phoneRepository.save(entity);
    }

    public PhoneData update(Long userId, Long phoneId, String phone) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        User user = optUser.get();
        Optional<PhoneData> optPhoneData = user.getPhones().stream()
                .filter(it -> it.getId().equals(phoneId))
                .findFirst();
        if (optPhoneData.isEmpty()) {
            throw new RequestException("phone not found");
        }
        PhoneData phoneData = optPhoneData.get();
        phoneData.setPhone(phone);
        return phoneRepository.save(phoneData);
    }

    public void delete(Long userId, Long phoneId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        phoneRepository.deleteById(phoneId);
    }
}
