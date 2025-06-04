package duke.movie.controller;

import duke.movie.entity.User;
import duke.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

   private final UserRepository userRepository;

   @GetMapping
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   @PostMapping
   public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
      if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
         return ResponseEntity.badRequest().build();
      }

      User savedUser = userRepository.save(user);
      return ResponseEntity.ok(savedUser);
   }
}