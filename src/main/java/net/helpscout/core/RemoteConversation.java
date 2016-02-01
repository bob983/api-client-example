package net.helpscout.core;

import net.helpscout.CoreApi;
import net.helpscout.core.Conversation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rx.Observable;

import javax.websocket.server.PathParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(url = CoreApi.URL, name = "Core-Conversations")
@RequestMapping(value = "/core-api/v1/conversation")
public interface RemoteConversation {

    @RequestMapping(method = POST)
    Observable<Conversation> create(Conversation conversation);

    @RequestMapping(value = "/{id}",method = GET)
    Observable<Conversation> get(@PathVariable("id") Long id);

}
