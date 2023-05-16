package br.mil.ccarj.controle.acesso.services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeycloackService {

    final Keycloak keycloak;

    @Autowired
    public KeycloackService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public List<UserRepresentation> findUsersRealm(
            String realmName,
            String userName,
            String firstName,
            String lastName,
            String email,
            Integer first,
            Integer max
    ) {
        return keycloak
                .realm(realmName)
                .users()
                .search(userName, firstName, lastName, email, first, max);
    }

    public List<UserRepresentation> findUsersRealm(String realmName, String search, Integer first, Integer max) {
        return keycloak
                .realm(realmName)
                .users()
                .search(search, first, max);
    }

    public Integer countRealmUsers(String realmName, String search) {
        return keycloak
                .realm(realmName)
                .users()
                .count(search);
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
        return keycloak
                .realm(realmName)
                .roles()
                .list();
    }

    public List<RoleRepresentation> findUserAvaliabeRealmRoles(String realmName, String userId) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .listAvailable();
    }

    public List<RoleRepresentation> findUserAvaliabeClientRoles(String realmName, String userId, String clientId) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientId)
                .listAvailable();
    }

    public List<RoleRepresentation> findUserRealmRoles(String realmName, String userId) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .listAll();
    }

    public List<RoleRepresentation> findUserClientRoles(String realmName, String userId, String clientId) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientId)
                .listAll();
    }

    public void addRealmRoleMappingUser(String realmName, String userId, List<RoleRepresentation> roles) {
        keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roles);
    }

    public void removeRealmRoleMappingUser(String realmName, String userId, List<RoleRepresentation> roles) {
        keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .remove(roles);
    }

    public void addClientRoleMappingUser(String realmName, String userId, String clientId, List<RoleRepresentation> roles) {
        keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientId)
                .add(roles);
    }

    public void removeClientRoleMappingUser(String realmName, String userId, String clientId, List<RoleRepresentation> roles) {
        keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientId)
                .remove(roles);
    }

    public List<ClientRepresentation> findallRealmClients(String realmName) {
        return keycloak
                .realm(realmName)
                .clients()
                .findAll();
    }

    public List<GroupRepresentation> findAllRealmGroups(String realmName) {
        return keycloak
                .realm(realmName)
                .groups()
                .groups();
    }

    public GroupRepresentation findRealmGroupsById(String realmName, String groupId) {
        return keycloak
                .realm(realmName)
                .groups()
                .group(groupId)
                .toRepresentation();
    }

    public List<GroupRepresentation> findUserRealmGroups(String realmName, String userId) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .groups();
    }

    public List<GroupRepresentation> findUserRealmGroups(String realmName,
                                                         String userId,
                                                         String search,
                                                         Integer first,
                                                         Integer max) {
        return keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .groups(search, first, max);
    }
    public void addRealmGroupUser(String realmName, String userId, String groupId){
        keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .joinGroup(groupId);
    }
    public void removeRealmGroupUser(String realmName, String userId, String groupId){
        keycloak
                .realm(realmName)
                .users()
                .get(userId)
                .leaveGroup(groupId);
    }

    public List<UserRepresentation> findGroupMembers(String realmName, String groupId) {
        int first = 0;
        int max = 5;
        boolean continuar = true;
        List<UserRepresentation> groupMembers = new ArrayList<>();
        while (continuar) {
            List<UserRepresentation> current = keycloak
                    .realm(realmName)
                    .groups()
                    .group(groupId)
                    .members(first, max);
            if(!current.isEmpty()){
                first += max;
                groupMembers.addAll(current);
            } else {
                first=0;
                continuar = false;
            }
        }
        return groupMembers;
    }

}
