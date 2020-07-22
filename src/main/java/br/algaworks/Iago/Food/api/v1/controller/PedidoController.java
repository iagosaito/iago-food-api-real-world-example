package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.PedidoModelAssembler;
import br.algaworks.Iago.Food.api.v1.assembler.PedidoResumoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.PedidoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.PedidoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.PedidoModel;
import br.algaworks.Iago.Food.api.v1.model.dto.PedidoResumoModel;
import br.algaworks.Iago.Food.api.v1.model.input.PedidoInput;
import br.algaworks.Iago.Food.core.data.PageWrapper;
import br.algaworks.Iago.Food.core.data.PageableTranslator;
import br.algaworks.Iago.Food.core.security.AlgaSecurity;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.exception.EntidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.NegocioException;
import br.algaworks.Iago.Food.domain.model.Pedido;
import br.algaworks.Iago.Food.domain.model.Usuario;
import br.algaworks.Iago.Food.domain.repository.PedidoRepository;
import br.algaworks.Iago.Food.domain.filter.PedidoFilter;
import br.algaworks.Iago.Food.domain.service.EmissaoPedidoService;
import br.algaworks.Iago.Food.domain.service.UsuarioService;
import br.algaworks.Iago.Food.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedPedidoAssembler;

    @Autowired
    private AlgaSecurity algaSecurity;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = pedidoRepository.findAll();
//        List<PedidoResumoModel> pedidoModels = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//
//        var pedidosWrapper = new MappingJacksonValue(pedidoModels);
//
//        var filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @CheckSecurity.Pedidos.PodePesquisar
    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filter,
                                                   @PageableDefault(size = 10) Pageable pageable) {

        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidosPage =
                pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

//        List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
//                .toCollectionModel(pedidosPage.getContent());

        return pagedPedidoAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);

    }

    @CheckSecurity.Pedidos.PodeBuscar
    @Override
    @GetMapping("/{codigoPedido}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(emissaoPedidoService.buscar(codigoPedido));
    }

    @CheckSecurity.Pedidos.PodeCriarPedido
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoModel> salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);


        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(algaSecurity.getUsuarioId());

        try {
            pedido = emissaoPedidoService.emitir(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoModelAssembler.toModel(pedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(pageable, mapeamento);

    }
}
