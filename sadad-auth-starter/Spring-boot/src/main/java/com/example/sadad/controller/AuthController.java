package com.example.sadad.controller;
import java.util.List;
import java.util.stream.Collectors;
import com.example.sadad.dto.AuthRequest;
import com.example.sadad.dto.AuthResponse;
import com.example.sadad.dto.RegisterRequest;
import com.example.sadad.service.JwtService;
import com.example.sadad.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        String role = (request.getRole() == null || request.getRole().isBlank())
                ? "ROLE_ADMIN"
                : (request.getRole().toUpperCase().startsWith("ROLE_")
                ? request.getRole().toUpperCase()
                : "ROLE_" + request.getRole().toUpperCase());

        userService.register(request.getUsername(), request.getPassword(), role);
        return ResponseEntity.ok(Map.of("message","User has been created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // هترجع "ENTRY"
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("refreshToken", refreshToken);
        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String,String> body){
        String refreshToken = body.get("refreshToken");
        try {
            String username = jwtService.extractUsername(refreshToken);

            var userDetails = userService.loadUserByUsername(username);
            String newAccess = jwtService.generateToken(userDetails);

            var roles = userDetails.getAuthorities()
                    .stream()
                    .map(auth -> auth.getAuthority())
                    .toList();

            return ResponseEntity.ok(new AuthResponse(newAccess, refreshToken, roles));
        } catch (Exception ex){
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of("user", auth.getName(), "roles", auth.getAuthorities()));
    }




}
