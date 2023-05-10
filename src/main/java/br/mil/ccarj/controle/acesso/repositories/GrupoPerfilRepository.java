package br.mil.ccarj.controle.acesso.repositories;

import br.mil.ccarj.controle.acesso.models.GrupoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoPerfilRepository extends JpaRepository<GrupoPerfil, String> {
}
