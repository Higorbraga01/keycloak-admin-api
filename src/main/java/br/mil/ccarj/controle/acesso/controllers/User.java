package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        System.out.println("first:"+first +" max:"+ max);
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

    @GetMapping("/realm/{realmName}/user/count")
    public Integer contarUsuariosRealm(@PathVariable String realmName, @RequestParam(required = false) String search) {
        return keycloackService.countRealmUsers(realmName,search);
    }

    @GetMapping("/realm/{realmName}/user/{id}")
    public UserRepresentation buscarUsuario(@PathVariable String realmName, @PathVariable String id) {
        return keycloackService.findUserId(realmName, id);
    }

    @GetMapping("/realm/{realmName}/user/{id}/groups")
    public List<GroupRepresentation> buscarGruposDoUsuario(@PathVariable String realmName, @PathVariable String id) {
        return keycloackService.findUserRealmGroups(realmName, id);
    }

    @PutMapping("/realm/{realmName}/user/{id}/groups/{groupId}")
    public ResponseEntity<?> entrarEmUmGrupo(@PathVariable String realmName, @PathVariable String id, @PathVariable String groupId) {
        keycloackService.addRealmGroupUser(realmName, id, groupId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/realm/{realmName}/user/{id}/groups/{groupId}")
    public ResponseEntity<?> deixarUmGrupo(@PathVariable String realmName, @PathVariable String id, @PathVariable String groupId) {
        keycloackService.removeRealmGroupUser(realmName, id, groupId);
        return ResponseEntity.noContent().build();
    }
}
