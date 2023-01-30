package pk.ketanprabhu.pubsub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pk.ketanprabhu.pubsub.service.PubSubService;

@RestController
@RequestMapping("/api/v1/pubsub/")
public class PubSubController {

	@Autowired
	PubSubService pubSubService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity sendMessage(){
		return pubSubService.SendMessage();
	}
	
}
