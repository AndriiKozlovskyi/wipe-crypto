package org.example.user;

import jakarta.validation.Valid;
import org.example.user.dtos.AuthUserDto;
import org.example.user.dtos.RegisterRequest;
import org.example.user.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "${base-path}/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "User API", description = "Here you can find username, email and another profile info")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest request) {
        User user = null;
        try {
            user = userService.create(request);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        UserResponse o = modelMapper.map(user, UserResponse.class);
        System.out.println(o);
        return ResponseEntity.ok(o);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        return new ResponseEntity<>(userService.usernameExists(username), HttpStatus.OK);
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.emailExists(email), HttpStatus.OK);
    }

    @GetMapping("/findBy/{identifier}")
    public ResponseEntity<AuthUserDto> findByIdentifier(@PathVariable String identifier) {
        return ResponseEntity.ok(modelMapper.map(userService.getByIdentifier(identifier), AuthUserDto.class));
    }

//    @PutMapping("/update")
//    public ResponseEntity<UserDto> updateUserById(@Valid @RequestPart UserUpdateRequest request,
//                                                  @RequestPart(required = false) MultipartFile file) {
//        return ResponseEntity.ok(modelMapper.map(userService.updateUserById(request, file), UserDto.class));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
