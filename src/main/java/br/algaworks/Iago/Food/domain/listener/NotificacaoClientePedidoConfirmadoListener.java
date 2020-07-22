package br.algaworks.Iago.Food.domain.listener;

import br.algaworks.Iago.Food.domain.event.PedidoConfirmadoEvent;
import br.algaworks.Iago.Food.domain.model.Pedido;
import br.algaworks.Iago.Food.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

//    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        EnvioEmailService.Mensagem mensagem =
                EnvioEmailService.Mensagem.builder()
                        .assunto(pedido.getRestaurante().getNome() + "- Pedido Confirmado")
                        .templateHtmlCorpo("email-pedido-confirmado.html")
                        .variavel("pedido", pedido)
                        .destinatario(pedido.getCliente().getEmail())
                        .build();


        envioEmailService.enviar(mensagem);
    }

}
