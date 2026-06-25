package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

/**
 * Routes to the profile
 * Requires user to be logged-in
 */
@RestController
@RequestMapping("profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private ProfileService profileService;
    private UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    /**
     * @param principal Used to identify the user
     * @return profile from the table
     */
    @GetMapping
    public Profile viewProfile(Principal principal) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return profileService.getByUserId(userId);
    }

    /**
     * @param principal Used to identify the user
     * @param profile   Updated profile after use sends it
     * @return The user Id and updated profile.
     */
    @PutMapping
    public Profile updateProfile(Principal principal, @RequestBody Profile profile) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return profileService.updateProfile(userId, profile);
    }
}
