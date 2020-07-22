package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.api.v1.model.input.SenhaInput;
import br.algaworks.Iago.Food.domain.exception.EntidadeEmUsoException;
import br.algaworks.Iago.Food.domain.exception.NegocioException;
import br.algaworks.Iago.Food.domain.exception.UsuarioNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.Grupo;
import br.algaworks.Iago.Food.domain.model.Usuario;
import br.algaworks.Iago.Food.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de id %d não pode ser excluído, pois está em uso";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario buscar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format(
                    "Já existe um usuário cadastrado com o e-mail: %s", usuario.getEmail()));
        }

        if (usuario.isNovo()) {
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        }
    }

    @Transactional
    public void atualizarSenha(Usuario usuario, SenhaInput senhaInput) {

        if (!passwordEncoder.matches(usuario.getSenha(), senhaInput.getSenhaAtual())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(senhaInput.getNovaSenha());
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);

        usuario.removerGrupo(grupo);
    }
}
