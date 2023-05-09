package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import br.mil.ccarj.controle.acesso.services.RelatorioService;
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
    private final RelatorioService relatorioService;

    @Autowired
    public Group(KeycloackService service, RelatorioService relatorioService) {
        this.service = service;
        this.relatorioService = relatorioService;
    }

    @GetMapping("/realm/{realmName}/groups")
    public List<GroupRepresentation> buscarTodosGruposPorRealm(@PathVariable String realmName) {
        return service.findAllRealmGroups(realmName);
    }

    @GetMapping("/realm/{realmName}/groups/{groupId}")
    public GroupRepresentation buscarGrupoPorId(@PathVariable String realmName, @PathVariable String groupId) {
        return service.findRealmGroupsById(realmName, groupId);
    }
    @GetMapping("/realm/{realmName}/groups/{groupId}/members")
    public void gerarRelatorioDeUsuariosPorGrupo(@PathVariable String realmName, @PathVariable String groupId) {
        this.relatorioService.generateFile();
    }
}
