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

    public List<UserRepresentation> findUsersRealm(String realmName, String userName, String firstName, String lastName, String email, Integer first, Integer max) {
        return keycloak
                .realm(realmName)
                .users()
                .search(userName, firstName, lastName, email, first, max);
    }

    public UserRepresentation findUserId(String realmName, String userId) {
        return keycloak
                .realms()
                .realm(realmName)
                .users()
                .get(userId)
                .toRepresentation();

    }

    public List<RoleRepresentation> findAllRealmRoles(String realmName) {
        return keycloak.realm(realmName).roles().list();
    }

    public List<RoleRepresentation> findUserAvaliabeRealmRoles(String realmName, String userId) {
        return keycloak.realm(realmName).users().get(userId).roles().realmLevel().listAvailable();
    }

    public List<RoleRepresentation> findUserAvaliabeClientRoles(String realmName, String userId, String clientId) {
        return keycloak.realm(realmName).users().get(userId).roles().clientLevel(clientId).listAvailable();
    }

    public List<RoleRepresentation> findUserRealmRoles(String realmName, String userId) {
        return keycloak.realm(realmName).users().get(userId).roles().realmLevel().listAll();
    }

    public List<RoleRepresentation> findUserClientRoles(String realmName, String userId, String clientId) {
        return keycloak.realm(realmName).users().get(userId).roles().clientLevel(clientId).listAll();
    }

    public void addRealmRoleMappingUser(String realmName, String userId, List<RoleRepresentation> roles) {
        keycloak.realm(realmName).users().get(userId).roles().realmLevel().add(roles);
    }

    public void removeRealmRoleMappingUser(String realmName, String userId, List<RoleRepresentation> roles) {
        keycloak.realm(realmName).users().get(userId).roles().realmLevel().remove(roles);
    }

}
