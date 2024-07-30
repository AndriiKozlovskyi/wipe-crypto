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
@Tag(name = "Example API", description = "API for demonstrating Swagger integration")
public class UserController {
    @GetMapping
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>("Hello world!", HttpStatus.OK);
    }

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody RegisterRequest request) {
        User user = null;
        try {
            user = userService.saveUser(request);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok(modelMapper.map(user, UserResponse.class));

    }

//    @GetMapping("/getAll")
//    public ResponseEntity<List<UserDto>> getAll() {
//        return ResponseEntity.ok(userService.getAll().stream()
//                .map(user -> modelMapper.map(user, UserDto.class)).toList());
//    }

//    @GetMapping("/getUserById/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
//        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
//    }
//
//    @GetMapping("/getUserByEmail/{email}")
//    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(modelMapper.map(userService.getUserByEmail(email), UserDto.class));
//    }

    @GetMapping("/findBy/{identifier}")
    public ResponseEntity<AuthUserDto> findByIdentifier(@PathVariable String identifier) {
        return ResponseEntity.ok(modelMapper.map(userService.getByIdentifier(identifier), AuthUserDto.class));
    }

//    @PutMapping("/update")
//    public ResponseEntity<UserDto> updateUserById(@Valid @RequestPart UserUpdateRequest request,
//                                                  @RequestPart(required = false) MultipartFile file) {
//        return ResponseEntity.ok(modelMapper.map(userService.updateUserById(request, file), UserDto.class));
//    }

//    @DeleteMapping("/deleteUserById/{id}")
//    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
//        userService.deleteUserById(id);
//        return ResponseEntity.ok().build();
//    }
}
