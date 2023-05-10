package br.mil.ccarj.controle.acesso.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "grupos_perfis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoPerfil implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "nome_grupo_perfil")
    private String name;
    @Column(name = "sistema_id")
    private String identificadorSistema;
}
