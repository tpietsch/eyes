package security.service;

import database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import security.util.CurrentUserUtil;

import java.util.regex.Pattern;

@Component
public class SpringSecurityExpressions {

    @Autowired
    CurrentUserUtil currentUserUtil;

    @Autowired
    UserRepository userRepository;

    private Pattern pattern = Pattern.compile("ROLE_(.*)");
}