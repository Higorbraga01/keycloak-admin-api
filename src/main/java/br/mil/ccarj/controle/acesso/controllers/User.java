package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class User {

    final KeycloackService keycloackService;

    @Autowired
    public User(KeycloackService keycloackService) {
        this.keycloackService = keycloackService;
    }

    @GetMapping("/realm/{realmName}")
    public List<UserRepresentation> buscarUsuariosPorRealm(@PathVariable String realmName,
                                                           @RequestParam(required = false) String userName,
                                                           @RequestParam(required = false) String firstName,
                                                           @RequestParam(required = false) String lastName,
                                                           @RequestParam(required = false) String email,
                                                           @RequestParam(required = false) Integer first,
                                                           @RequestParam(required = false) Integer max) {
        return keycloackService
                .findUsersRealm(
                        realmName,
                        userName,
                        firstName,
                        lastName,
                        email,
                        first,
                        max
                );
    }

    @GetMapping("/realm/{realmName}/user/{id}")
    public UserRepresentation buscarUsuario(@PathVariable String realmName, @PathVariable String id) {
        return keycloackService.findUserId(realmName, id);
    }
}
