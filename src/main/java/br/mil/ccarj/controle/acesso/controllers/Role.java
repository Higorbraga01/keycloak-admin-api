package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/avaliable/realm/{realmName}/user/{userId}")
    public List<RoleRepresentation> buscarRolesDisponiveisUsuarioRealm(@PathVariable String realmName, @PathVariable String userId) {
        return keycloackService.findUserAvaliabeRealmRoles(realmName, userId);
    }

    @GetMapping("/avaliable/realm/{realmName}/user/{userId}/client/{clientId}")
    public List<RoleRepresentation> buscarRolesDisponiveisUsuarioClient(@PathVariable String realmName, @PathVariable String userId, @PathVariable String clientId) {
        return keycloackService.findUserAvaliabeClientRoles(realmName, userId, clientId);
    }

    @PostMapping("/realm/{realmName}/user/{userId}")
    public void adicionarRealmRolesUser(@PathVariable String realmName, @PathVariable String userId, @RequestBody List<RoleRepresentation> roles) {
         keycloackService.addRealmRoleMappingUser(realmName, userId, roles);
    }

    @DeleteMapping("/realm/{realmName}/user/{userId}")
    public void removerRealmRolesUser(@PathVariable String realmName, @PathVariable String userId, @RequestBody List<RoleRepresentation> roles) {
        keycloackService.removeRealmRoleMappingUser(realmName, userId, roles);
    }

}
