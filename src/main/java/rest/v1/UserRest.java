package rest.v1;


import database.models.TweetEntity;
import database.repositories.TweetRepository;
import database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rest.v1.models.ErrorJsonResponse;

import static rest.RestEndpointConstants.*;

@RestController
@RequestMapping(value = V1 + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRest {
    private static Logger logger = Logger.getLogger(UserRest.class);

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> getReceipts() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET,value = "/" + USER_ID_PATH)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> getReceipts(@PathVariable(USER_ID) String userId) {
        return ResponseEntity.ok(userRepository.findOne(userId));
    }

}


