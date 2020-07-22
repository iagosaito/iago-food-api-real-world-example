package br.algaworks.Iago.Food.api.v2.disassembler;

import br.algaworks.Iago.Food.api.v1.model.input.CidadeInput;
import br.algaworks.Iago.Food.api.v2.model.input.CidadeInputV2;
import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {

        // Workaround para evitar que o JPA entenda que eu esteja
        // trocando a chave primária do objeto.
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
 }
