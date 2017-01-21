package security.util;

import database.models.UserEntity;
import database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import security.models.EyesUser;

@Component
public class CurrentUserUtil {
    @Autowired
    UserRepository mUserRepository;

    public UserEntity getCurrentUser() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
               return null;
            }
            String username = ((EyesUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            return mUserRepository.findByEmail(username);
        } catch (Exception e) {
            return null;
        }
    }
}
