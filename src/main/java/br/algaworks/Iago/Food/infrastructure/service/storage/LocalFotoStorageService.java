package br.algaworks.Iago.Food.infrastructure.service.storage;

import br.algaworks.Iago.Food.core.storage.StorageProperties;
import br.algaworks.Iago.Food.domain.service.FotoStorageService;
import br.algaworks.Iago.Food.infrastructure.service.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        // Caminho para onde queremos armazenar a foto
        Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

        try {
            //Copiamos o arquivo de um lugar para outro.
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path arquivoPath = getArquivoPath(nomeArquivo);

        try {
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        Path arquivoPath = getArquivoPath(nomeArquivo);

        try {
            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(arquivoPath)).build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo.", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
