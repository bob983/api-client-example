package net.helpscout;

import javaslang.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import net.helpscout.converter.ConvertedRequestBody;
import net.helpscout.converter.MappedType;
import net.helpscout.core.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping("api/v1/conversations")
public class ConversationsController {

    private final RemoteConversation remoteConversation;

    @Autowired
    public ConversationsController(RemoteConversation remoteConversation) {
        this.remoteConversation = remoteConversation;
    }

    @RequestMapping(method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Conversation createConversation(@ConvertedRequestBody Conversation conversation) {
        return remoteConversation.create(conversation);
    }

    @RequestMapping(method = GET)
    @MappedType(Conversation.class)
    public List<Conversation> getConversations() {
        return Stream.gen(Math::random)
                .take(5000)
                .map(n -> (n * 10000))
                .map(i -> new Conversation(i.longValue()))
                .toJavaList();
    }

}
