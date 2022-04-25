package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.User;
import com.filipiakp.warehousespring.entities.dto.UserDTO;
import com.filipiakp.warehousespring.model.UserRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRepository userRepository;

  @RequestMapping("/users")
  public String getAll(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "users";
  }

  @RequestMapping("/users/add")
  public String add(Model model) {
    model.addAttribute("userDTO", new UserDTO());
    return "userForm";
  }

  @RequestMapping(
      value = "/saveUser",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      method = RequestMethod.POST)
  public String saveUser(@Valid UserDTO user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "userForm";
    }

    if (!user.getNewPassword().equals(user.getNewPasswordRepeated())) {
      bindingResult.rejectValue("newPasswordRepeated", "error.userDTO", "Hasła muszą się zgadzać");
      return "userForm";
    }

    Optional<User> repositoryUser = userRepository.findByUsername(user.getUsername());

    if (repositoryUser.isPresent()) {
      User oldUser = repositoryUser.get();
      oldUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
      userRepository.save(oldUser);
      return "redirect:/users";
    }

    User newUser =
        User.builder()
            .username(user.getUsername())
            .password(passwordEncoder.encode(user.getNewPassword()))
            .role("user")
            .build();
    userRepository.save(newUser);

    return "redirect:/users";
  }

  @RequestMapping("/users/edit/{username}")
  public String edit(@PathVariable String username, Model model) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (!userOptional.isPresent()) {
      return "redirect:/users/add";
    }
    model.addAttribute("userDTO", UserDTO.builder().username(username).build());
    return "userForm";
  }

  @RequestMapping("/users/delete/{username}")
  public String delete(@PathVariable String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }
    return "redirect:/users";
  }
}
