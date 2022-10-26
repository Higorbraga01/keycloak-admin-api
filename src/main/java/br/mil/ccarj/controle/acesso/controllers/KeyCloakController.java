package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/keycloack")
public class KeyCloakController {

    @Autowired
    KeycloackService keycloackService;

    @GetMapping("/{realmName}/usuarios")
    public List<UserRepresentation> buscarUsuariosPorRealm(@PathVariable String realmName) {
        return keycloackService.buscarUsuariosDoRealm(realmName);
    }

    @GetMapping("/{realmName}/usuarios/{userId}/roles")
    public List<RoleRepresentation> buscarRolesUsuario(@PathVariable String realmName, @PathVariable String userId) {
        return keycloackService.buscarRolesUsuario(realmName, userId);
    }

}
