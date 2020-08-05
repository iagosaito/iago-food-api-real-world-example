package br.algaworks.Iago.Food.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;


/**
 * Spring segue o caminho da configuração.
 * A variável local será: iagofood.storage.local.diretorio-fotos.
 * A atribuição é imediata.
 */

@Getter
@Setter
@Component
@ConfigurationProperties("iagofood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo = TipoStorage.LOCAL;

    public enum TipoStorage {
        LOCAL, S3
    }

    @Getter
    @Setter
    public static class Local {
        private Path diretorioFotos;
    }

    @Getter
    @Setter
    public static class S3 {
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private String diretorioFotos;
        private Regions regiao;
    }

}
