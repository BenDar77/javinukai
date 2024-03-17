package lt.javinukai.javinukai.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.javinukai.javinukai.entity.Contest;
import lt.javinukai.javinukai.entity.PhotoCollection;
import lt.javinukai.javinukai.entity.User;
import lt.javinukai.javinukai.repository.ContestRepository;
import lt.javinukai.javinukai.repository.PhotoCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateService {
    private final UserService userService;
    private final PhotoCollectionRepository photoCollectionRepository;
    private final ContestRepository contestRepository;

    public void rateLike(UUID juryId, UUID collectionId) {
        if (checkIfCollectionLikedByJury(juryId, collectionId)) {
            return;
        }
        PhotoCollection collectionToLike = photoCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new EntityNotFoundException("Contest was not found with ID: " + collectionId));
        collectionToLike.addLike(userService.getUser(juryId));
        updateLikesCount(collectionToLike);
    }

    public void undoLike(UUID juryId, UUID collectionId) {
        PhotoCollection collectionToUndoLike = photoCollectionRepository.findByUserIdAndPhotoCollectionId(juryId, collectionId);
        if (checkIfCollectionLikedByJury(juryId, collectionId)) {
            collectionToUndoLike.removeLike(userService.getUser(juryId));
            updateLikesCount(collectionToUndoLike);
        }
    }

    public void updateLikesCount(PhotoCollection collectionToUpdate) {
        collectionToUpdate.setLikesCount(collectionToUpdate.getJuryLikes().size());
        photoCollectionRepository.save(collectionToUpdate);
    }
    public void updateCollections(List<PhotoCollection> collectionsToUpdate){
        photoCollectionRepository.saveAll(collectionsToUpdate);
    }

        public List<User> juryWhoLikesCollection(UUID collectionId) {
        return photoCollectionRepository.findAllJuryByCollectionId(collectionId);
    }

    public List<PhotoCollection> findCollectionsLikedByJury(UUID juryId) {
        return photoCollectionRepository.findLikedCollectionsByJuryId(juryId);
    }

    public boolean checkIfCollectionLikedByJury(UUID juryId, UUID collectionId) {
        PhotoCollection collection = photoCollectionRepository.findByUserIdAndPhotoCollectionId(juryId, collectionId);
        if (collection == null) {
            return false;
        }
        return true;
    }

    public List<PhotoCollection> findAllLikedCollections() {
        return photoCollectionRepository.findAllLikedCollections();
    }

    public List<PhotoCollection> findCollectionsWithZeroLikes() {
        return photoCollectionRepository.findCollectionsWithNoLikes();
    }
    public List<PhotoCollection> findCollectionsWithZeroLikesInContest(UUID contestId){
        Contest c = contestRepository.getReferenceById(contestId);
        return photoCollectionRepository.findCollectionsWithNoLikesInContest(c);
    }

    public List<PhotoCollection> findCollectionsWithLikesInContest(UUID contestId){
        Contest c = contestRepository.getReferenceById(contestId);
        return photoCollectionRepository.findCollectionsWithLikesInContest(c);
    }
    public List<PhotoCollection> findAllCollectionsInContest(UUID contestId){
        Contest c = contestRepository.getReferenceById(contestId);
        return photoCollectionRepository.findCollectionsInContest(c);
    }

    public void deleteAllLikes(List<PhotoCollection> collections) {
        collections.forEach(PhotoCollection::removeAllLikesFromCollection);
        photoCollectionRepository.saveAll(collections);
    }


}
