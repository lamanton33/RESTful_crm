package server.api.Tag;

import commons.Result;
import commons.Tag;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;
    private final SimpMessagingTemplate msg;

    public TagController(TagService tagService, SimpMessagingTemplate msg) {
        this.tagService = tagService;
        this.msg = msg;
    }

    /**
     * Put request to update the Tag with id {id}
     */
    @PutMapping("/update/{id}")
    public Result<Tag> updateTag(@RequestBody Tag tag, @PathVariable UUID id) {
        var result = tagService.updateTag(tag, id);
        msg.convertAndSend("/topic/update-card/", tag.card.cardID);
        return result;
    }


}
