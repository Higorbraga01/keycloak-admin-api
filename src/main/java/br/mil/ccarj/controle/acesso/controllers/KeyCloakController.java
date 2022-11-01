package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keycloack")
public class KeyCloakController {


    final KeycloackService keycloackService;

    @Autowired
    public KeyCloakController(KeycloackService keycloackService) {
        this.keycloackService = keycloackService;
    }

    @GetMapping("/{realmName}/usuarios")
    public List<UserRepresentation> buscarUsuariosPorRealm(@PathVariable String realmName,
                                                           @RequestParam(required = false) String userName,
                                                           @RequestParam(required = false) String firstName,
                                                           @RequestParam(required = false) String lastName,
                                                           @RequestParam(required = false) String email,
                                                           @RequestParam(required = false) Integer first,
                                                           @RequestParam(required = false) Integer max) {
        return keycloackService
                .buscarUsuariosDoRealm(
                        realmName,
                        userName,
                        firstName,
                        lastName,
                        email,
                        first,
                        max
                );
    }

    @GetMapping("/{realmName}/usuarios/{userId}/roles")
    public List<RoleRepresentation> buscarRolesUsuario(@PathVariable String realmName, @PathVariable String userId) {
        return keycloackService.buscarRolesUsuario(realmName, userId);
    }

}
