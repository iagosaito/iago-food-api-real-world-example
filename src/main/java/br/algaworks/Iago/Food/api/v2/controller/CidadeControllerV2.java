package br.algaworks.Iago.Food.api.v2.controller;

import br.algaworks.Iago.Food.api.ResourceUriHelper;
import br.algaworks.Iago.Food.api.v1.assembler.CidadeModelAssembler;
import br.algaworks.Iago.Food.api.v1.disassembler.CidadeInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.CidadeModel;
import br.algaworks.Iago.Food.api.v1.model.input.CidadeInput;
import br.algaworks.Iago.Food.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.algaworks.Iago.Food.api.v2.assembler.CidadeModelAssemblerV2;
import br.algaworks.Iago.Food.api.v2.disassembler.CidadeInputDisassemblerV2;
import br.algaworks.Iago.Food.api.v2.model.dto.CidadeModelV2;
import br.algaworks.Iago.Food.api.v2.model.input.CidadeInputV2;
import br.algaworks.Iago.Food.api.v2.openapi.controller.CidadeControllerOpenApiV2;
import br.algaworks.Iago.Food.core.web.IagoMediaTypes;
import br.algaworks.Iago.Food.domain.exception.EstadoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.exception.NegocioException;
import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.repository.CidadeRepository;
import br.algaworks.Iago.Food.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping(path = "/cidades", produces = IagoMediaTypes.V2_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerOpenApiV2 {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssemblerV2 cidadeModelAssembler;

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeModelV2 buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscar(id);
        return cidadeModelAssembler.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CidadeModelV2> adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try{
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            CidadeModelV2 novaCidade = cidadeModelAssembler.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(novaCidade.getIdCidade());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(novaCidade);

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeModelV2> atualizar(@RequestBody @Valid CidadeInputV2 cidadeInput, @PathVariable Long id) {
            Cidade cidadeAtual = cidadeService.buscar(id);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            try {
                CidadeModelV2 cidadeSalva = cidadeModelAssembler.toModel(cidadeService.salvar(cidadeAtual));
                return ResponseEntity.ok(cidadeSalva);
            } catch (EstadoNaoEncontradoException e) {
                throw new NegocioException(e.getMessage(), e);
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> excluir(@PathVariable Long id){
        cidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
