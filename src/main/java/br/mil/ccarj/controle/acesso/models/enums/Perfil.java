package br.mil.ccarj.controle.acesso.models.enums;

public enum Perfil {
    SISPLAER_ADMIN(1, "Sisplaer Admin"),
    SISPLAER_CADASTRO_BASICO(2, "Sisplaer Cadastro Basico"),
    SISPLAER_GERENTE_AO(3, "Sisplaer Gerente AO"),
    SISPLAER_GERENTE_ODS(4, "Sisplaer Gerente ODS"),
    SISPLAER_GERENTE_ODS_AO_2000(5, "Sisplaer Gerente ODS AO 2000"),
    SISPLAER_GERENTE_ODS_UGR(6, "Sisplaer Gerente ODS UGR"),
    SISPLAER_GERENTE_PO(7, "Sisplaer Gerente PO"),
    SISPLAER_GERENTE_UGR(8, "Sisplaer Gerente UGR");

    private int valor;
    private String nome;

    Perfil(int valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

    public static Perfil forInt(int id) {
        for (Perfil perfil : values()) {
            if (perfil.valor == id) {
                return perfil;
            }
        }
        throw new IllegalArgumentException("Perfil selecionado invalido, entre em contato com o administrador do sistema: " + id);
    }

    public String getNome() {
        return nome;
    }
}
