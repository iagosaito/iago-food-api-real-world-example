package br.algaworks.Iago.Food.api.v1.disassembler;

import br.algaworks.Iago.Food.api.v1.model.input.CidadeInput;
import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {

        // Workaround para evitar que o JPA entenda que eu esteja
        // trocando a chave primária do objeto.
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
 }
