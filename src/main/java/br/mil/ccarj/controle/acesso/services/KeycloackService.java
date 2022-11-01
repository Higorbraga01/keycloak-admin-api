package br.mil.ccarj.controle.acesso.services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloackService {

    final Keycloak keycloak;

    @Autowired
    public KeycloackService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public List<UserRepresentation> buscarUsuariosDoRealm(String realmName, String userName, String firstName, String lastName, String email, Integer first, Integer max) {
        return keycloak
                .realm(realmName)
                .users()
                .search(userName, firstName, lastName, email, first, max);
    }

    public List<RoleRepresentation> buscarRolesUsuario(String realmName, String userId) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .listAll();
    }
}
