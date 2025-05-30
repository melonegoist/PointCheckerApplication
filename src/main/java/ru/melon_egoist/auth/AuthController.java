package ru.melon_egoist.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.melon_egoist.auth.registration.ErrorResponse;
import ru.melon_egoist.auth.registration.RegistrationRequest;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new Exception(ex.getMessage() + "Error, wrong username!" + request.getUsername());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        if (userExists(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("User already exists!"));
        }

        createUser(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(new AuthenticationResponse("New user created successfully!"));
    }

    private boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private void createUser(String username, String password) {
        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(password);

        userRepository.save(newUser);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody ValidateRequest request) {
        String token = request.getToken();
        String username = jwtTokenUtil.extractUsername(token);

        UserDetails details = userDetailsService.loadUserByUsername(username);


        if (jwtTokenUtil.validateToken(token, details)) {
            return ResponseEntity.ok(new ValidateResponse(true, username));
        }

        return ResponseEntity.ok(new ValidateResponse(false));
    }
}
