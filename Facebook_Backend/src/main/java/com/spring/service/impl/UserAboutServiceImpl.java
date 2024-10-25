package com.spring.service.impl;

import com.spring.dto.Request.User.UserAboutRequest;
import com.spring.dto.Response.User.UserAboutResponse;
import com.spring.entities.Location;
import com.spring.entities.UserAbout;
import com.spring.repository.LocationRepository;
import com.spring.repository.UserAboutRepository;
import com.spring.repository.UserRepository;
import com.spring.service.UserAboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserAboutServiceImpl implements UserAboutService {

    @Autowired
    private UserAboutRepository userAboutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    private UserAboutResponse mapToResponse(UserAbout userAbout) {
        return UserAboutResponse.builder()
                .firstName(userAbout.getUser().getFirstName())
                .lastName(userAbout.getUser().getLastName())
                .email(userAbout.getUser().getEmail())
                .phone(userAbout.getUser().getPhoneNumber())
                .dateOfBirth(userAbout.getUser().getDateOfBirth())
                .gender(userAbout.getUser().getGender().toString())
                .userName(userAbout.getUserName())
                .occupation(userAbout.getOccupation())
                .workPlace(userAbout.getWorkPlace())
                .educationLevel(userAbout.getEducationLevel().toString())
                .school(userAbout.getSchool())
                .dateOfJoining(userAbout.getDateOfJoining())
                .locationName(userAbout.getLocation() != null
                        ? userAbout.getLocation().getCity() + ", " + userAbout.getLocation().getCountry()
                        : null)
                .relationshipStatus(userAbout.getRelationship().toString())
                .build();
    }

    @Override
    public List<UserAboutResponse> findAll() {
        List<UserAbout> userAboutList = userAboutRepository.findAll();
        return userAboutList.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserAboutResponse findById(Integer id) {
        UserAbout userAbout = userAboutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserAbout not found with id: " + id));
        return mapToResponse(userAbout);
    }

    @Override
    public UserAboutResponse save(UserAboutRequest userAboutRequest, Integer userId) {
        UserAbout existingUserAbout = userAboutRepository.findByUserId(userId);

        UserAbout userAbout;
        if (existingUserAbout != null) {
            userAbout = existingUserAbout;
        } else {
            userAbout = new UserAbout();
            userAbout.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user")));
        }

        if (userAboutRequest.getFirstName() != null) {
            userAbout.getUser().setFirstName(userAboutRequest.getFirstName());
        }
        if (userAboutRequest.getLastName() != null) {
            userAbout.getUser().setLastName(userAboutRequest.getLastName());
        }
        if (userAboutRequest.getEmail() != null) {
            userAbout.getUser().setEmail(userAboutRequest.getEmail());
        }
        if (userAboutRequest.getPhone() != null) {
            userAbout.getUser().setPhoneNumber(userAboutRequest.getPhone());
        }
        if (userAboutRequest.getDateOfBirth() != null) {
            userAbout.getUser().setDateOfBirth(userAboutRequest.getDateOfBirth());
        }
        if (userAboutRequest.getGender() != null) {
            userAbout.getUser().setGender(userAboutRequest.getGender());
        }
        if (userAboutRequest.getUserName() != null) {
            userAbout.setUserName(userAboutRequest.getUserName());
        }
        if (userAboutRequest.getOccupation() != null) {
            userAbout.setOccupation(userAboutRequest.getOccupation());
        }
        if (userAboutRequest.getWorkPlace() != null) {
            userAbout.setWorkPlace(userAboutRequest.getWorkPlace());
        }
        if (userAboutRequest.getEducationLevel() != null) {
            userAbout.setEducationLevel(userAboutRequest.getEducationLevel());
        }
        if (userAboutRequest.getSchool() != null) {
            userAbout.setSchool(userAboutRequest.getSchool());
        }
        if (userAboutRequest.getDateOfJoining() != null) {
            userAbout.setDateOfJoining(userAboutRequest.getDateOfJoining());
        }
        if (userAboutRequest.getRelationshipStatus() != null) {
            userAbout.setRelationship(userAboutRequest.getRelationshipStatus());
        }
        if (userAboutRequest.getLocation() != null) {
            if (Boolean.TRUE.equals(userAboutRequest.getLocation())) {
                Location newLocation = new Location();
                newLocation.setCity(userAboutRequest.getCity());
                newLocation.setCountry(userAboutRequest.getCountry());
                newLocation.setState(userAboutRequest.getState());
                newLocation.setStreet(userAboutRequest.getStreet());
                newLocation.setRegion(userAboutRequest.getRegion());
                newLocation.setDateCreated(new Date());
                newLocation.setDateUpdated(new Date());
                Location savedLocation = locationRepository.save(newLocation);
                userAbout.setLocation(savedLocation);
            } else {
                if (userAbout.getLocation() != null) {
                    locationRepository.deleteById(userAbout.getLocation().getId());
                    userAbout.setLocation(null);
                }
            }
        }

        UserAbout savedUserAbout = userAboutRepository.save(userAbout);
        return mapToResponse(savedUserAbout);
    }


    @Override
    public void deleteById(Integer id) {
        UserAbout userAbout = userAboutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserAbout not found with id: " + id));

        if (userAbout.getLocation() != null) {
            userAbout.setLocation(null);
            userAboutRepository.save(userAbout);
        }

        userAbout.setUser(null);
        userAboutRepository.save(userAbout);

        userAboutRepository.deleteById(id);
    }
}
