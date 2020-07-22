package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.CozinhaModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.CozinhaControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.CozinhaInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.CozinhaModel;
import br.algaworks.Iago.Food.api.v1.model.input.CozinhaInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import br.algaworks.Iago.Food.domain.service.CozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

//    private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourceAssembler;

    @CheckSecurity.Cozinhas.PodeConsultar
    @Override
    @GetMapping
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        log.info("Consultando Cozinhas...");
        log.info("Consultando Cozinhas com páginas de {} registros", pageable.getPageSize());

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourceAssembler
                .toModel(cozinhasPage, cozinhaModelAssembler);

        return cozinhasPagedModel;

//        List<CozinhaModel> cozinhaModel =
//                cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
//
//        return new PageImpl<>(cozinhaModel, pageable, cozinhasPage.getTotalElements());
    }

//    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//    public CozinhasXmlWrapper listarXml(){
//        return new CozinhasXmlWrapper(cozinhaRepository.findAll());
//    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @Override
    @GetMapping(value = "/{id}")
    public CozinhaModel buscar(@PathVariable Long id){
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        return cozinhaModelAssembler.toModel(cozinha);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaModelAssembler.toModel(cozinha);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @Override
    @PutMapping("/{id}")
    public CozinhaModel atualizar(@PathVariable Long id,
                             @RequestBody @Valid CozinhaInput cozinhaInput){

        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinhaAtual));


        /*
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);

        if (cozinhaAtual.isPresent()) {
            // Copia todas as propriedades, inclusive o ID nulo, a menos que passe o 3 parâmetro
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");

            Cozinha cozinhaSalva = cozinhaService.salvar(cozinhaAtual.get());

            return ResponseEntity.ok(cozinhaSalva);
        }

        return ResponseEntity.notFound().build();

         */
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
//
//        try {
//            cozinhaService.excluir(id);
//            return ResponseEntity.noContent().build();
////        } catch (EntidadeNaoEncontradaException ex) {
////            return ResponseEntity.notFound().build();
//        } catch (EntidadeEmUsoException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }
}
