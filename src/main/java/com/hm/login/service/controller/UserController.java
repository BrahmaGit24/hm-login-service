package com.hm.login.service.controller;


import com.hm.login.service.dto.Result;
import com.hm.login.service.dto.UserDetailsDTO;
import com.hm.login.service.dto.UserSearch;
import com.hm.login.service.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save-user")
    @PreAuthorize("hasAnyAuthority('Super User')")
    public ResponseEntity<?> saveEmployees(@RequestBody UserDetailsDTO details) {
        Result result = userService.saveUserModel(details);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/search-user")
    @PreAuthorize("hasAnyAuthority('Super User','Admin')")
    public ResponseEntity<?> searchEmployee(@RequestBody UserSearch search) {
        Result result = userService.searchUser(search);
        return ResponseEntity.ok(result);
    }
}
