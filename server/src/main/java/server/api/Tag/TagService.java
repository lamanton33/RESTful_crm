package server.api.Tag;

import commons.Result;
import commons.Tag;
import org.springframework.stereotype.Service;
import server.database.TagRepository;

import java.util.UUID;

/**
 * Handles business logic of the Tag endpoints
 */
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Updates the name and colour of the Tag with id {id},
     * with the data of the given Tag tag.
     */
    public Result<Tag> updateTag(Tag tag, UUID id) {
        if(id == null){
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            return Result.SUCCESS.of(tagRepository.findById(id)
                    .map(t -> {
                        System.out.println("Updating tag: " + t.tagTitle + " to " + tag.tagTitle);
                        t.setTagTitle(tag.tagTitle);
                        t.setTagColor(tag.tagColor);
                        return tagRepository.save(t);
                    }).get());
        }catch (Exception e){
            return Result.FAILED_UPDATE_TAG;
        }
    }
    /**
     * Creates the name and colour of the Tag with id {id},
     * with the data of the given Tag tag.
     */
    /**
     * Creates a new Tag with the given data.
     */
    public Result<Tag> createTag(Tag tag, UUID id) {
        if (tag == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            Tag createdTag = tagRepository.save(tag);
            System.out.println("New tag created with ID: " + createdTag.getTagID());
            return Result.SUCCESS.of(createdTag);
        } catch (Exception e) {
            return Result.FAILED_CREATE_TAG;
        }
    }





}
