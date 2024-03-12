package lt.javinukai.javinukai.repository;


import lt.javinukai.javinukai.entity.ParticipationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, UUID> {
    List<ParticipationRequest> findByUserIdAndContestId(UUID userId, UUID contestID);

    Page<ParticipationRequest> findByUserSurnameContainingIgnoreCase(PageRequest pageRequest, String contains);
}
