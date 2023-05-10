package br.mil.ccarj.controle.acesso.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Group implements Serializable {

    private String id;
    private String name;
}
