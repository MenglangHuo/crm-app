package com.bronx.crm.domain.identity.user.service.impl;

import com.bronx.crm.domain.identity.user.dto.profile.UserProfileRequest;
import com.bronx.crm.domain.identity.user.dto.profile.UserProfileResponse;
import com.bronx.crm.domain.identity.user.entity.User;
import com.bronx.crm.domain.identity.user.entity.UserProfile;
import com.bronx.crm.domain.identity.user.mapper.UserProfileMapper;
import com.bronx.crm.domain.identity.user.repository.UserRepository;
import com.bronx.crm.domain.identity.user.repository.UserProfileRepository;
import com.bronx.crm.domain.identity.user.service.UserProfileService;
import com.bronx.crm.common.exception.ConflictException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final UserProfileMapper profileMapper;


    @Override
    public UserProfileResponse create(UserProfileRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId()));

        if (profileRepository.existsByUserId(request.userId())) {
            throw new ConflictException("UserProfile", "userId", request.userId());
        }

        UserProfile profile = profileMapper.toEntity(request);
        profile.setUser(user);

        UserProfile saved = profileRepository.save(profile);
        return profileMapper.toResponse(saved);
    }

    @Override
    public UserProfileResponse get(Long id) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", id));
        return profileMapper.toResponse(profile);
    }

    @Override
    public UserProfileResponse delete(Long id) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", id));
        profile.setDeletedAt(LocalDateTime.now());
       return profileMapper.toResponse(profileRepository.save(profile));
    }

    @Override
    public UserProfileResponse update(Long id, UserProfileRequest request) {
        UserProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", id));


        profileMapper.updateEntityFromRequest(request, profile);
        UserProfile updated = profileRepository.save(profile);
        return profileMapper.toResponse(updated);
    }
    

    @Override
    public UserProfileResponse findByUserId(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile", "userId", userId));
        return profileMapper.toResponse(profile);
    }
}
