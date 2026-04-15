package com.stories.stories.config;

import com.stories.stories.models.Profile;
import com.stories.stories.models.Story;
import com.stories.stories.models.User;
import com.stories.stories.repositories.StoryRepository;
import com.stories.stories.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public DataSeeder(
            UserRepository userRepository,
            StoryRepository storyRepository
    ) {
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0 || storyRepository.count() > 0) {
            return;
        }

        User user1 = new User();
        user1.setUserName("sara");
        user1.setEmailAddress("sara@example.com");
        user1.setPassword("password123");
        user1.setAccountVerified(true);
        user1.setActivated(true);

        Profile profile1 = new Profile();
        profile1.setFirstName("Sara");
        profile1.setLastName("Noor");
        profile1.setProfileDescription("I enjoy writing short mystery stories.");
        user1.setProfile(profile1);

        User user2 = new User();
        user2.setUserName("omar");
        user2.setEmailAddress("omar@example.com");
        user2.setPassword("password123");
        user2.setAccountVerified(true);
        user2.setActivated(true);

        Profile profile2 = new Profile();
        profile2.setFirstName("Omar");
        profile2.setLastName("Ali");
        profile2.setProfileDescription("I like adventure and travel stories.");
        user2.setProfile(profile2);

        User user3 = new User();
        user3.setUserName("lina");
        user3.setEmailAddress("lina@example.com");
        user3.setPassword("password123");
        user3.setAccountVerified(true);
        user3.setActivated(true);

        Profile profile3 = new Profile();
        profile3.setFirstName("Lina");
        profile3.setLastName("Khaled");
        profile3.setProfileDescription("I write calm and thoughtful stories.");
        user3.setProfile(profile3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Story story1 = new Story();
        story1.setTitle("A Day at the Old Market");
        story1.setContent("Sara found a small box in the market, and it had a letter from 1978.");
        story1.setPublishDate(LocalDateTime.now().minusDays(3));
        story1.setProfile(user1.getProfile());

        Story story2 = new Story();
        story2.setTitle("The Road to the Desert");
        story2.setContent("Omar traveled by bus and discovered a small village with kind people.");
        story2.setPublishDate(LocalDateTime.now().minusDays(2));
        story2.setProfile(user2.getProfile());

        Story story3 = new Story();
        story3.setTitle("Rain in the City");
        story3.setContent("Lina sat near the window and wrote about a quiet rainy afternoon.");
        story3.setPublishDate(LocalDateTime.now().minusDays(1));
        story3.setProfile(user3.getProfile());

        Story story4 = new Story();
        story4.setTitle("The Last Train Home");
        story4.setContent("Sara missed her train but met an old friend at the station.");
        story4.setPublishDate(LocalDateTime.now());
        story4.setProfile(user1.getProfile());

        storyRepository.save(story1);
        storyRepository.save(story2);
        storyRepository.save(story3);
        storyRepository.save(story4);
    }
}
