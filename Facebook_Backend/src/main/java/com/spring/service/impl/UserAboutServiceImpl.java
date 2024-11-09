package com.spring.service.impl;

import com.spring.dto.Request.User.UserAboutRequest;
import com.spring.dto.response.User.UserAboutResponse;
import com.spring.entities.Location;
import com.spring.entities.Photo;
import com.spring.entities.UserAbout;
import com.spring.repository.LocationRepository;
import com.spring.repository.PhotoRepository;
import com.spring.repository.UserAboutRepository;
import com.spring.repository.UserRepository;
import com.spring.service.UserAboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Autowired
    private PhotoRepository photoRepository;

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
                .educationLevel(userAbout.getEducationLevel() != null ? userAbout.getEducationLevel().toString() : null)
                .school(userAbout.getSchool())
                .dateOfJoining(userAbout.getDateOfJoining())
                .locationName(userAbout.getLocation() != null
                        ? userAbout.getLocation().getCity() + ", " + userAbout.getLocation().getCountry()
                        : null)
                .relationshipStatus(userAbout.getRelationship() != null ? userAbout.getRelationship().toString() : null)
                .backgroundUrl(userAbout.getBackground() != null ? userAbout.getBackground().getImageUrl() : null)
                .profilePhotoUrl(userAbout.getProfilePhoto() != null ? userAbout.getProfilePhoto().getImageUrl() : null)
                .avatarPhotoUrl(userAbout.getAvatar() != null ? userAbout.getAvatar().getImageUrl() : null)
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
        if (userAboutRequest.getBackgroundUrl() != null) {
            if (userAbout.getBackground() != null) {
                photoRepository.deleteById(userAbout.getBackground().getId());
            }
            Photo photo = new Photo();
            photo.setUserId(userId);
            photo.setImageUrl(userAboutRequest.getBackgroundUrl());
            photo.setUploadDate(Instant.now());
            photo.setBackground(userAbout);
            photoRepository.save(photo);
            userAbout.setBackground(photo);
        }
        if (userAboutRequest.getAvatarPhotoUrl() != null) {
            if (userAbout.getAvatar() != null) {
                photoRepository.deleteById(userAbout.getAvatar().getId());
            }
            Photo avatarPhoto = new Photo();
            avatarPhoto.setUserId(userId);
            avatarPhoto.setImageUrl(userAboutRequest.getAvatarPhotoUrl());
            avatarPhoto.setUploadDate(Instant.now());
            avatarPhoto.setAvatar(userAbout);
            photoRepository.save(avatarPhoto);
            userAbout.setAvatar(avatarPhoto);
            userRepository.save(userAbout.getUser());
        }
        if (userAboutRequest.getProfilePhotoUrl() != null) {
            if (userAbout.getProfilePhoto() != null) {
                photoRepository.deleteById(userAbout.getProfilePhoto().getId());
            }
            Photo profilePhoto = new Photo();
            profilePhoto.setUserId(userId);
            profilePhoto.setImageUrl(userAboutRequest.getProfilePhotoUrl());
            profilePhoto.setUploadDate(Instant.now());
            profilePhoto.setProfilePhoto(userAbout);
            photoRepository.save(profilePhoto);
            userAbout.setProfilePhoto(profilePhoto);
        }

        UserAbout savedUserAbout = userAboutRepository.save(userAbout);
        return mapToResponse(savedUserAbout);
    }
}
