package br.algaworks.Iago.Food.api.v1.disassembler;

import br.algaworks.Iago.Food.api.v1.model.input.UsuarioInput;
import br.algaworks.Iago.Food.api.v1.model.input.UsuarioComSenhaInput;
import br.algaworks.Iago.Food.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioComSenhaInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}
