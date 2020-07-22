package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.FormaPagamentoController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.FormaPagamentoModel;
import br.algaworks.Iago.Food.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public FormaPagamentoModelAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
    }

    @Override
    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel = modelMapper.map(formaPagamento, FormaPagamentoModel.class);

        formaPagamentoModel.add(links.linkToFormaPagamento(formaPagamentoModel.getId()));
        formaPagamentoModel.add(links.linkToFormasPagamento("formas-pagamento"));

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(links.linkToFormasPagamento("formas-pagamento"));
    }

    //    public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formaPagamentos) {
//        return formaPagamentos.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
