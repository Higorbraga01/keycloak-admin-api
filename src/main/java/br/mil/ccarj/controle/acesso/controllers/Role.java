package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class Role {

    final KeycloackService keycloackService;

    @Autowired
    public Role(KeycloackService keycloackService) {
        this.keycloackService = keycloackService;
    }

    @GetMapping("/realm/{realmName}")
    public List<RoleRepresentation> buscarRolesRealm(@PathVariable String realmName){
        return keycloackService.findAllRealmRoles(realmName);
    }

    @GetMapping("/realm/{realmName}/user/{userId}")
    public List<RoleRepresentation> buscarRolesUsuarioRealm(@PathVariable String realmName, @PathVariable String userId) {
        return keycloackService.findUserRealmRoles(realmName, userId);
    }

    @GetMapping("/realm/{realmName}/user/{userId}/client/{clientId}")
    public List<RoleRepresentation> buscarRolesUsuarioClient(@PathVariable String realmName, @PathVariable String userId, @PathVariable String clientId) {
        return keycloackService.findUserClientRoles(realmName, userId, clientId);
    }

}