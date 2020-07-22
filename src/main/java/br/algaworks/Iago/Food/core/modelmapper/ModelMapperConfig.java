package br.algaworks.Iago.Food.core.modelmapper;

import br.algaworks.Iago.Food.api.v1.model.dto.EnderecoModel;
import br.algaworks.Iago.Food.api.v1.model.input.ItemPedidoInput;
import br.algaworks.Iago.Food.api.v2.model.input.CidadeInputV2;
import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.model.Endereco;
import br.algaworks.Iago.Food.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
//        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//                .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);



        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        /*
         * "value" é o valor que foi retornado na primeira expressão lambda, ou seja
         * o "source"
         */
        enderecoToEnderecoModelTypeMap.<String>addMapping(
            enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
            (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value)
        );

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapping -> mapping.skip(ItemPedido::setId));

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapping -> mapping.skip(Cidade::setId));


        return modelMapper;
    }

}