package br.mil.ccarj.controle.acesso.services;

import br.mil.ccarj.controle.acesso.models.GrupoPerfil;
import br.mil.ccarj.controle.acesso.repositories.GrupoPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrupoPerfilService {

    private final GrupoPerfilRepository repository;

    @Autowired
    public GrupoPerfilService(GrupoPerfilRepository repository) {
        this.repository = repository;
    }

    public GrupoPerfil createGrupoPerfil(GrupoPerfil grupoPerfil) {
        return repository.save(grupoPerfil);
    }

    public List<GrupoPerfil> createGrupoPerfilList(List<GrupoPerfil> grupoPerfils) {
        return repository.saveAll(grupoPerfils);
    }

    public List<GrupoPerfil> findAll() {
        return repository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(GrupoPerfil::getOrdem))
                .collect(Collectors.toList());
    }

    public GrupoPerfil buscarPorId(String idGrupo) {
        Optional<GrupoPerfil> grupoPerfil = repository.findById(idGrupo);
        if(grupoPerfil.isPresent()) {
            return grupoPerfil.get();
        } else{
            throw new RuntimeException("Perfil não encontrado");
        }
    }
}
