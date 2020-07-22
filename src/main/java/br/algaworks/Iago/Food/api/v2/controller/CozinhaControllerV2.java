package br.algaworks.Iago.Food.api.v2.controller;

import br.algaworks.Iago.Food.api.v1.assembler.CozinhaModelAssembler;
import br.algaworks.Iago.Food.api.v1.disassembler.CozinhaInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.CozinhaModel;
import br.algaworks.Iago.Food.api.v1.model.input.CozinhaInput;
import br.algaworks.Iago.Food.api.v1.openapi.controller.CozinhaControllerOpenApi;
import br.algaworks.Iago.Food.api.v2.assembler.CozinhaModelAssemblerV2;
import br.algaworks.Iago.Food.api.v2.disassembler.CozinhaInputDisassemblerV2;
import br.algaworks.Iago.Food.api.v2.model.dto.CozinhaModelV2;
import br.algaworks.Iago.Food.api.v2.model.input.CozinhaInputV2;
import br.algaworks.Iago.Food.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import br.algaworks.Iago.Food.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaModelAssemblerV2 cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourceAssembler;

    @GetMapping
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaModelV2> cozinhasPagedModel = pagedResourceAssembler
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

    @GetMapping(value = "/{id}")
    public CozinhaModelV2 buscar(@PathVariable Long id){
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        return cozinhaModelAssembler.toModel(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cozinhaService.salvar(cozinha);
        return cozinhaModelAssembler.toModel(cozinha);
    }

    @PutMapping("/{id}")
    public CozinhaModelV2 atualizar(@PathVariable Long id,
                             @RequestBody @Valid CozinhaInputV2 cozinhaInput){

        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaService.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }

}
