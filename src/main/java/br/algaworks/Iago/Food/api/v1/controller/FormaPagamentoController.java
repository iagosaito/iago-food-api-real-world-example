package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.FormaPagamentoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.FormaPagamentoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.FormaPagamentoModel;
import br.algaworks.Iago.Food.api.v1.model.input.FormaPagamentoInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.FormaPagamento;
import br.algaworks.Iago.Food.domain.repository.FormaPagamentoRepository;
import br.algaworks.Iago.Food.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formaPagamentos = formaPagamentoRepository.findAll();

        CollectionModel<FormaPagamentoModel> formasPagamentoModel =
                formaPagamentoModelAssembler.toCollectionModel(formaPagamentos);

        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .eTag(eTag)
                .body(formasPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id,
                                                      ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamentoModel formaPagamentoModel =
                formaPagamentoModelAssembler.toModel(formaPagamentoService.buscar(id));

        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);

        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(formaPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FormaPagamentoModel> salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento =
                formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        formaPagamento = formaPagamentoService.salvar(formaPagamento);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> atualizar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput, @PathVariable Long id) {
        FormaPagamento formaPagamento = formaPagamentoService.buscar(id);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);
        FormaPagamento formaPagamentoAtualizada = formaPagamentoService.salvar(formaPagamento);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamentoAtualizada);

        return ResponseEntity.ok(formaPagamentoModel);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        formaPagamentoService.excluir(id);
    }
}
