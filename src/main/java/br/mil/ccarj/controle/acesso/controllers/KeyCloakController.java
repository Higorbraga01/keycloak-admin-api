package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class KeyCloakController {

    @Autowired
    KeycloackService keycloackService;

    @GetMapping("/keycloack/usuarios/{realmName}")
    public List<UserRepresentation> buscarUsuariosPorRealm(@PathVariable String realmName) {
        return keycloackService.buscarUsuariosDoRealm(realmName);
    }

}
