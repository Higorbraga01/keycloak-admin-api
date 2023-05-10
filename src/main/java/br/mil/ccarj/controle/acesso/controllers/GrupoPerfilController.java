package br.mil.ccarj.controle.acesso.controllers;

import br.mil.ccarj.controle.acesso.models.GrupoPerfil;
import br.mil.ccarj.controle.acesso.services.GrupoPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos-perfis")
public class GrupoPerfilController {

    private final GrupoPerfilService service;

    @Autowired
    public GrupoPerfilController(GrupoPerfilService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Object> criarNovoGrupoPerfil(
            @RequestBody(required = false) GrupoPerfil grupoPerfil
    ) {
        return ResponseEntity.ok(service.createGrupoPerfil(grupoPerfil));
    }

    @PostMapping("/list")
    public ResponseEntity<Object> criarNovoGrupoPerfil(
            @RequestBody(required = false) List<GrupoPerfil> grupoPerfilList
    ) {
        return ResponseEntity.ok(service.createGrupoPerfilList(grupoPerfilList));
    }

    @GetMapping
    public ResponseEntity<List<GrupoPerfil>> buscarTodosOsGruposPerfisSisplaer() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoPerfil> buscarGrupoPerfilPorId(@PathVariable String id) {
        return  ResponseEntity.ok(service.buscarPorId(id));
    }
}
