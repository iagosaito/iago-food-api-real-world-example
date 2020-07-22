package br.algaworks.Iago.Food.infrastructure.service.storage;

import br.algaworks.Iago.Food.core.storage.StorageProperties;
import br.algaworks.Iago.Food.domain.service.FotoStorageService;
import br.algaworks.Iago.Food.infrastructure.service.storage.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getContentType());

            // Preparando Payload da requisição na API da Amazon
            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetaData
            ).withCannedAcl(CannedAccessControlList.PublicRead); // Permissão para leitura pública

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), caminhoArquivo);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

        try {
            URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

            return FotoRecuperada.builder()
                    .url(url.toString()).build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar a foto", e);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
