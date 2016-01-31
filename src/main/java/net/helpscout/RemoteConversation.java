package net.helpscout;

import net.helpscout.core.Conversation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(url = CoreApi.URL, name = "Core-Conversations")
public interface RemoteConversation {

    @RequestMapping(value = "/core-api/v1/conversation", method = POST)
    Conversation create(Conversation conversation);

}
