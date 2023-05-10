package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.models.GrupoPerfil;
import br.mil.ccarj.controle.acesso.services.KeycloackService;
import br.mil.ccarj.controle.acesso.services.RelatorioService;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final KeycloackService service;
    private final RelatorioService relatorioService;

    @Autowired
    public GroupController(KeycloackService service, RelatorioService relatorioService) {
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
    public List<UserRepresentation> gerarRelatorioDeUsuariosPorGrupo(@PathVariable String realmName, @PathVariable String groupId) {
        return service.findGroupMembers(realmName,groupId);
    }

    @PostMapping(value = "/realm/{realmName}/groups/members", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public byte[] gerarRelatorioDeUsuariosPorGrupo(HttpServletResponse response,@PathVariable String realmName, @RequestBody List<GrupoPerfil> grupoPerfilList) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=USUARIOS_SISPLAER_POR_PERFIS.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return relatorioService.generateFile(realmName, grupoPerfilList);
    }
}
