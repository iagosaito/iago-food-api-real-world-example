package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.EstadoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.EstadoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.EstadoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.EstadoModel;
import br.algaworks.Iago.Food.api.v1.model.input.EstadoInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Estado;
import br.algaworks.Iago.Food.domain.repository.EstadoRepository;
import br.algaworks.Iago.Food.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @CheckSecurity.Estado.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @CheckSecurity.Estado.PodeConsultar
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> buscar(@PathVariable Long id){
        EstadoModel estadoModel = estadoModelAssembler.toModel(estadoService.buscar(id));
        return ResponseEntity.ok(estadoModel);
    }

    @CheckSecurity.Estado.PodeEditar
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput){
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        estado = estadoService.salvar(estado);
        return estadoModelAssembler.toModel(estado);
    }

    @CheckSecurity.Estado.PodeEditar
    @Override
    @PutMapping("/{id}")
    public EstadoModel atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id){
        Estado estadoAtual = estadoService.buscar(id);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        Estado estadoSalvo = estadoService.salvar(estadoAtual);

        return estadoModelAssembler.toModel(estadoSalvo);
    }

    @CheckSecurity.Estado.PodeEditar
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        estadoService.excluir(id);
    }
}
