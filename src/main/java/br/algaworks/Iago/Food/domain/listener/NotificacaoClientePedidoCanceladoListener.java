package br.algaworks.Iago.Food.domain.listener;

import br.algaworks.Iago.Food.domain.event.PedidoCanceladoEvent;
import br.algaworks.Iago.Food.domain.model.Pedido;
import br.algaworks.Iago.Food.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
                .destinatario(pedido.getCliente().getEmail())
                .templateHtmlCorpo("email-pedido-cancelado.html")
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(mensagem);
    }

}
