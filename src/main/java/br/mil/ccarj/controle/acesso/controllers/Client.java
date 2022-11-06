package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.services.KeycloackService;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class Client {

    final KeycloackService keycloackService;

    @Autowired
    public Client(KeycloackService keycloackService) {
        this.keycloackService = keycloackService;
    }

    @GetMapping("/realm/{realmName}/clients")
    public List<ClientRepresentation> buscarClientsDoRealm(@PathVariable String realmName) {
        return keycloackService.findallRealmClients(realmName);
    }
}
