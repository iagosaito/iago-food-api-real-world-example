package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.RestauranteApenasNomeAssembler;
import br.algaworks.Iago.Food.api.v1.assembler.RestauranteBasicoModelAssembler;
import br.algaworks.Iago.Food.api.v1.assembler.RestauranteModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.RestauranteControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.RestauranteInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteApenasNomeModel;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteBasicoModel;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteModel;
import br.algaworks.Iago.Food.api.v1.model.input.RestauranteInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.exception.CidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.CozinhaNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.NegocioException;
import br.algaworks.Iago.Food.domain.exception.RestauranteNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepository;
import br.algaworks.Iago.Food.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteApenasNomeAssembler restauranteApenasNomeAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private SmartValidator validator;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
//        return ResponseEntity.ok()
////                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://www.iagofood.local:8000")
//                .body(restaurantesModel);
    }

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//        List<RestauranteModel> restauranteModels = restauranteModelAssembler.toCollectionModel(restaurantes);
//
//        var restaurantesWrapper = new MappingJacksonValue(restauranteModels);
//
//        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//        if ("apenas-nome".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//        } else if ("completo".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(null);
//        }
//
//        return restaurantesWrapper;
//    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
        return restauranteApenasNomeAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteModel> buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);
        return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));

    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

        try {
            Restaurante novoRestaurante = restauranteService.salvar(restaurante);
            return restauranteModelAssembler.toModel(novoRestaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{id}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long id) {

        try {
            Restaurante restauranteAtual = restauranteService.buscar(id);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            Restaurante novoRestaurante = restauranteService.salvar(restauranteAtual);
            return restauranteModelAssembler.toModel(novoRestaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/ativacoes}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/ativacoes}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        restauranteService.fechar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long id) {
        restauranteService.abrir(id);
        return ResponseEntity.noContent().build();
    }
}

//    @PatchMapping("/{id}")
//    public ResponseEntity<?> atualizarParcial(@PathVariable Long id,
//                                              @RequestBody Map<String, Object> campos,
//                                              HttpServletRequest request){
//
//        Restaurante restauranteAtual = restauranteService.buscar(id);
//
//        Restaurante restaurante = toDomainObject(restauranteInput);
//
//        merge(campos, restauranteAtual, request);
//
//
//        validate(restauranteAtual, "restaurante");
//
//        return atualizar(restauranteAtual, id);
//    }
//
//    private void validate(Restaurante restaurante, String objectName) {
//        var bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//
//        validator.validate(restaurante, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            throw new ValidacaoException(bindingResult);
//        }
//
//    }
//
//    /*
//        Reflections é a capacidade de alterar o objeto em tempo de execução
//        de forma dinâmica.
//     */
//
//    // Mesclar os valores dos campos para dentro do restaurante atual
//    private void merge(@RequestBody Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
//
//
//        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//        try {
//            /*
//                Classe responsável por converter objetos em JSON e vice versa
//             */
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//            // Converte os valores ao objeto automaticamente
//            Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
//
//            camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//                field.setAccessible(true);
//
//                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//                /*
//                Atribua o campo encontrado na variável de instância, no restaurante destino
//                de acordo com o valor de sua propriedade no MAP
//                 */
//    //            ReflectionUtils.setField(field, restauranteDestino, valorPropriedade);
//                ReflectionUtils.setField(field, restauranteDestino, novoValor);
//            });
//        } catch (IllegalArgumentException e) {
//            Throwable rootCause = ExceptionUtils.getRootCause(e);
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//        }
//    }

