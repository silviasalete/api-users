package com.programatriz.users.controller;

import com.programatriz.users.model.User;
import com.programatriz.users.model.UserDto;
import com.programatriz.users.service.UserService;
import com.programatriz.users.model.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "Create user", description = "Create and returns a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully - User created"),
            @ApiResponse(responseCode = "404", description = "Bad Request - Invalid role"),
            @ApiResponse(responseCode = "409", description = "Conflict - User already exist")
    })
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDto dto){

        HttpHeaders headers = new HttpHeaders();
        String validUser = isValid(dto);

        if (!validUser.isEmpty()) {
            headers.set("message",validUser);
            return new ResponseEntity<>(headers,HttpStatus.BAD_REQUEST);
        }

        User userCreated = service.create(dto);

        headers.set("message","USER CREATED");
        return new ResponseEntity<>(userCreated,headers,HttpStatus.OK);
    }

    private String isValid(UserDto dto) {
        if (service.findByEmail(dto.email()) != null){
            LOGGER.info("USER {} ALREADY EXIST",dto.email());
            return "USER ALREADY EXIST";
        };

        try {
            UserRole.valueOf(dto.role());
            return "";
        }catch (IllegalArgumentException e){
            LOGGER.info("INVALID ROLE {}",dto.role());
            return "INVALID ROLE USE {}";
        }
    }

    @Operation(summary = "List users", description = "List all users")
    @GetMapping
    public ResponseEntity<Iterable<User>> listAll(){
        return ResponseEntity.ok(service.listAll());
    }
}
