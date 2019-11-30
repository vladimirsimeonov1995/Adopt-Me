package adopt.service.services;

import adopt.service.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel model);

    UserServiceModel findUserByUsername(String username);

}
