package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile create(Profile profile) {
        return profileRepository.save(profile);
    }

    /**
     * @param userId Use the userId to find the profile from the table
     * @return the Profile object
     */
    public Profile getByUserId(int userId) {
        return profileRepository.findById(userId).orElse(null);
    }

    /**
     * Set the user Id for the profile to be able to save the updated profile
     * which will override the old profile info.
     *
     * @param userId
     * @param profile
     * @return
     */
    public Profile updateProfile(int userId, Profile profile) {
        profile.setUserId(userId);
        return profileRepository.save(profile);
    }
}
