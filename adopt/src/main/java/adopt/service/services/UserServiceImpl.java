package adopt.service.services;

import adopt.data.models.User;
import adopt.data.repositories.UserRepository;
import adopt.service.models.AnimalServiceModel;
import adopt.service.models.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository usersRepository, ModelMapper mapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = mapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        if (this.usersRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());

            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }
        
        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        return this.modelMapper.map(this.usersRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = this.usersRepository.findByUsername(username).orElse(null);

        if(user == null) {
            return null;
        }else {
            return this.modelMapper.map(user, UserServiceModel.class);
        }
    }

    @Override
    public UserServiceModel findUserById(String id) {
        User entity = this.usersRepository.findById(id).orElse(null);

        if(entity == null) {
            throw new IllegalArgumentException("No such user");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(entity, UserServiceModel.class);

        return userServiceModel;
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel model, String oldPassword) {
        User user = this.usersRepository.findByUsername(model.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException("Incorrect password!");
        }

        if (model.getPassword() != null) {
            user.setPassword(this.bCryptPasswordEncoder.encode(model.getPassword()));
        }

        user.setEmail(model.getEmail());

        UserServiceModel savedModel = this.modelMapper.map(this.usersRepository.saveAndFlush(user), UserServiceModel.class);

        return savedModel;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println();

        return this.usersRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }


}
