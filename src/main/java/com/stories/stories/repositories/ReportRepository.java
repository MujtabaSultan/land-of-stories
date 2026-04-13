package com.stories.stories.repositories;

import com.stories.stories.models.Profile;
import com.stories.stories.models.Report;
import com.stories.stories.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
   Report findByStoryAndProfile(Story story , Profile profile);
}
