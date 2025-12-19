package com.agtech.shop.controller;

import com.agtech.shop.model.dto.UserDTO;
import com.agtech.shop.model.formDto.UserFormDTO;
import com.agtech.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping( value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserFormDTO user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/sendInvoice")
    public ResponseEntity<?> sendInvoice(@RequestBody UserDTO user){
        userService.generateAndSendInvoice(user);
        return ResponseEntity.ok("Facture envoyé sur votre boite mail");
    }


}
