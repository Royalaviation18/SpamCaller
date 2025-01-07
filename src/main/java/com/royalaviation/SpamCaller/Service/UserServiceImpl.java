    package com.royalaviation.SpamCaller.Service;

    import com.royalaviation.SpamCaller.Config.JwtUtil;
    import com.royalaviation.SpamCaller.Entity.ContactEntity;
    import com.royalaviation.SpamCaller.Entity.UserEntity;
    import com.royalaviation.SpamCaller.Mapper.UserMapper;
    import com.royalaviation.SpamCaller.Model.UserDetailResponse;
    import com.royalaviation.SpamCaller.Model.UserModel;
    import com.royalaviation.SpamCaller.Repository.UserRepository;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Service
    public class UserServiceImpl implements UserService {
        @Autowired
        private UserMapper userMapper;
        @Autowired
        UserRepository userRepository;
        @Autowired
        private JwtUtil jwtUtil;
        @Autowired
        private PasswordEncoder passwordEncoder;



        @Override
        public String createUser(UserModel userModel) {

            if (userRepository.findByPhoneNumber(userModel.getPhoneNumber()).isPresent()) {
                return "Phone number already in use!";
            }


            if (userModel.getEmail() != null && userRepository.findByEmail(userModel.getEmail()).isPresent()) {
                return "Email is already in use!";
            }


            UserEntity userEntity = userMapper.userModelToUserEntity(userModel);


            String encodedPassword = passwordEncoder.encode(userModel.getPassword());
            userEntity.setPassword(encodedPassword);


            if (userModel.getContacts() != null && !userModel.getContacts().isEmpty()) {
                List<ContactEntity> contactEntities = userMapper.userModelContactsToContactEntity(userModel.getContacts());
                userEntity.setContacts(contactEntities);
            }

            userRepository.save(userEntity);

            return "User created successfully";
        }





        @Override
        public String loginUser(UserModel userModel) {
            Optional<UserEntity> userOptional = userRepository.findByEmail(userModel.getEmail());
            if(userOptional.isEmpty()){
                return  "Invalid email or password";
            }
            UserEntity userEntity = userOptional.get();
            if (!passwordEncoder.matches(userModel.getPassword(), userEntity.getPassword())) {
                return "Invalid email or password";
            }
            return jwtUtil.generateToken(userEntity.getEmail());

        }

        public String markPhoneNumberAsSpam(String phoneNumber) {
            // Get the currently authenticated user
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<UserEntity> userOptional = userRepository.findByEmail(username);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();
                // Find the user by the phone number and mark as spam
                Optional<UserEntity> phoneUserOptional = userRepository.findByPhoneNumber(phoneNumber);

                if (phoneUserOptional.isPresent()) {
                    UserEntity phoneUser = phoneUserOptional.get();
                    phoneUser.setSpam(true);
                    userRepository.save(phoneUser);
                    return "Phone number marked as spam successfully.";
                } else {
                    return "Phone number not found.";
                }
            } else {
                return "User not authenticated.";
            }
        }

        public List<UserEntity> searchUsersByName(String name) {
            List<UserEntity> usersStartingWith = userRepository.findByNameStartingWithIgnoreCase(name);
            List<UserEntity> usersContaining = userRepository.findByNameContainingIgnoreCase(name);

            usersStartingWith.addAll(usersContaining.stream()
                    .filter(user -> !usersStartingWith.contains(user))
                    .collect(Collectors.toList()));

            return usersStartingWith;
        }

        public List<UserEntity> searchUsersByPhoneNumber(String phoneNumber) {
            Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(phoneNumber);

            if (userOptional.isPresent()) {
                // If the phone number matches a registered user, return that user
                return List.of(userOptional.get());
            } else {
                // Otherwise, find all users with the same phone number (from different users' contact lists)
                return userRepository.findByPhoneNumberContaining(phoneNumber);
            }
        }

        public UserDetailResponse getUserDetails(String phoneNumber, String loggedInEmail) {
            Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(phoneNumber);
            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("User not found for the given phone number.");
            }

            UserEntity userEntity = userOptional.get();
            boolean isEmailVisible = false;

            // Check if the logged-in user is in the person's contact list
            if (userEntity.getContacts().stream()
                    .anyMatch(contact -> contact.getPhoneNumber().equals(loggedInEmail))) {
                isEmailVisible = true;
            }

            return new UserDetailResponse(
                    userEntity.getName(),
                    userEntity.getPhoneNumber(),
                    userEntity.isSpam() ? "Spam Number" : "Not Spam",
                    isEmailVisible ? userEntity.getEmail() : null
            );
        }

        @Override
        public String softDeleteUser(String phoneNumber) {
            Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(phoneNumber);

            if (userOptional.isPresent()) {
                UserEntity userEntity = userOptional.get();
                userEntity.setActive(false);
                userRepository.save(userEntity);
                return "User soft deleted successfully.";
            } else {
                return "User not found.";
            }
        }

        @Override
        public String updateUser(Long id, UserModel userModel) {
            Optional<UserEntity> userOptional = userRepository.findById(id);

            if (userOptional.isEmpty()) {
                return "User not found.";
            }

            UserEntity userEntity = userOptional.get();
            // Update fields from UserModel
            if (userModel.getName() != null) {
                userEntity.setName(userModel.getName());
            }
            if (userModel.getEmail() != null) {
                userEntity.setEmail(userModel.getEmail());
            }
            if (userModel.getPhoneNumber() != null) {
                userEntity.setPhoneNumber(userModel.getPhoneNumber());
            }
            if (userModel.getPassword() != null) {
                userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }

            // Save updated user entity
            userRepository.save(userEntity);
            return "User updated successfully.";
        }

        @Override
        public List<UserDetailResponse> getAllUserDetails() {
            // Fetch all users from the repository
            List<UserEntity> users = userRepository.findAll();


            return users.stream()
                    .map(user -> new UserDetailResponse(
                            user.getName(),
                            user.getPhoneNumber(),
                            user.isSpam() ? "Spam Number" : "Not Spam",
                            user.getEmail()))
                    .collect(Collectors.toList());
        }



    }
