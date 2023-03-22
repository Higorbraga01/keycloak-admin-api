package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class Group {

    private final KeycloackService service;

    @Autowired
    public Group(KeycloackService service) {
        this.service = service;
    }

    @GetMapping("/realm/{realmName}/groups")
    public List<GroupRepresentation> buscarTodosGruposPorRealm(@PathVariable String realmName) {
        return service.findAllRealmGroups(realmName);
    }

    @GetMapping("/realm/{realmName}/groups/{groupId}")
    public GroupRepresentation buscarGrupoPorId(@PathVariable String realmName, @PathVariable String groupId) {
        return service.findRealmGroupsById(realmName, groupId);
    }
}
