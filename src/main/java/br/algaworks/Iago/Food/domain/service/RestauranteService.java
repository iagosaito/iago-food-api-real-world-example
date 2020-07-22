package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.EntidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.RestauranteNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.*;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private UsuarioService usuarioService;

    public Restaurante buscar(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }


    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long idCozinha = restaurante.getCozinha().getId();
        Long idCidade = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalhar(idCozinha);
        Cidade cidade = cidadeService.buscar(idCidade);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscar(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscar(id);
//        restaurante.setAtivo(false);
        restaurante.inativar();
    }

    @Transactional
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarUsuarioResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = usuarioService.buscar(usuarioId);

        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
    public void desassociarUsuarioResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = usuarioService.buscar(usuarioId);

        restaurante.removerResponsavel(usuario);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);

        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);

        restaurante.fechar();
    }
}
