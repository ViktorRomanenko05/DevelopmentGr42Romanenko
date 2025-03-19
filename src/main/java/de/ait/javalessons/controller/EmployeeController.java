package de.ait.javalessons.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping("/public/info")
    public ResponseEntity<String> getPublicInfo() {
        return ResponseEntity.ok("User info, public information");
    }

    @GetMapping("/user/info")
    public ResponseEntity<String>getUserInfo(){
        return ResponseEntity.ok("User info, secured user information");
    }

    @GetMapping("/admin/info")
    public ResponseEntity<String>getAdminInfo(){
        return ResponseEntity.ok("User info, secured admin information");
    }
}
