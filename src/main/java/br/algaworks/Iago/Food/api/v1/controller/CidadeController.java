package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.ResourceUriHelper;
import br.algaworks.Iago.Food.api.v1.assembler.CidadeModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.CidadeInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.CidadeModel;
import br.algaworks.Iago.Food.api.v1.model.input.CidadeInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
// Versionamento por Media Type
//@RequestMapping(path = "/cidades", produces = IagoMediaTypes.V1_APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @CheckSecurity.Cidade.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        CollectionModel<CidadeModel> cidadesModel = cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());

        //        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());
        return cidadesModel;
    }

    @CheckSecurity.Cidade.PodeConsultar
    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscar(id);

        //        cidadeModel.getEstado().add(linkTo(EstadoController.class)
//                .slash(cidadeModel.getEstado().getId())
//                .withSelfRel());

//        cidadeModel.getEstado().add(new Link("http://api.iagofood.local:8080/estados/1"));

        return cidadeModelAssembler.toModel(cidade);
    }

    @CheckSecurity.Cidade.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CidadeModel> adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try{
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            CidadeModel novaCidade = cidadeModelAssembler.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(novaCidade.getId());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(novaCidade);

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidade.PodeEditar
    @PutMapping("/{id}")
    public ResponseEntity<CidadeModel> atualizar(@RequestBody @Valid CidadeInput cidadeInput, @PathVariable Long id) {
            Cidade cidadeAtual = cidadeService.buscar(id);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
            try {
                CidadeModel cidadeSalva = cidadeModelAssembler.toModel(cidadeService.salvar(cidadeAtual));
                return ResponseEntity.ok(cidadeSalva);
            } catch (EstadoNaoEncontradoException e) {
                throw new NegocioException(e.getMessage(), e);
            }
    }

    @CheckSecurity.Cidade.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> excluir(@PathVariable Long id){
        cidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
