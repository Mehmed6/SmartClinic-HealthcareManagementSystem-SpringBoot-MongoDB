package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.UserProfileRequest;
import com.doganmehmet.app.dto.response.UserProfileDTO;
import com.doganmehmet.app.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService m_userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileDTO> save(@Valid @RequestBody UserProfileRequest request)
    {
        return ResponseEntity.ok(m_userProfileService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_userProfileService.findUserProfileById(id));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<UserProfileDTO> findByUsername(@PathVariable String username)
    {
        return ResponseEntity.ok(m_userProfileService.findUserProfileByUsername(username));
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserProfileDTO> findByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(m_userProfileService.findUserProfileByEmail(email));
    }

    @PutMapping("update")
    public ResponseEntity<UserProfileDTO> update(@Valid @RequestBody UserProfileRequest request)
    {
        return ResponseEntity.ok(m_userProfileService.updateUserProfile(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        m_userProfileService.deleteUserProfileById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/current")
    public ResponseEntity<Void> deleteCurrentUserProfile()
    {
        m_userProfileService.deleteUserProfile();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_userProfileService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
