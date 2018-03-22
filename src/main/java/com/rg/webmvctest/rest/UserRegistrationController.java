package com.rg.webmvctest.rest;

import com.rg.webmvctest.domain.RegistrationService;
import com.rg.webmvctest.domain.UserRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/registrations")
public class UserRegistrationController {

    private final RegistrationService registrationService;

    public UserRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ExistingUserRegistrationDTO register(@RequestBody @Valid NewUserRegistrationDTO newUserRegistration) {
        UserRegistration userRegistration = registrationService.registerUser(
                newUserRegistration.getEmailAddress(),
                newUserRegistration.getName(), newUserRegistration.getLastName()
        );

        return asDTO(userRegistration);
    }

    @PutMapping(path = "/{registrationId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ExistingUserRegistrationDTO updateRegistration(
            @RequestBody @Valid NewUserRegistrationDTO newUserRegistration,
            @PathVariable("registrationId") String registrationId
    ) {
        UserRegistration userRegistration = registrationService.updateRegistration(asUserRegistration(newUserRegistration, registrationId));
        return asDTO(userRegistration);
    }


    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ExistingUserRegistrationDTO> getAllRegistrations() {
        return registrationService.findAllUserRegistrations().stream().map(this::asDTO).collect(Collectors.toList());
    }

    private ExistingUserRegistrationDTO asDTO(UserRegistration registration) {
        return new ExistingUserRegistrationDTO(
                registration.getRegistrationId(),
                registration.getEmailAddress(),
                registration.getName(),
                registration.getLastName()
        );
    }

    private UserRegistration asUserRegistration(NewUserRegistrationDTO dto, String registrationId) {
        return new UserRegistration(registrationId, dto.getEmailAddress(), dto.getName(), dto.getLastName());
    }
}
