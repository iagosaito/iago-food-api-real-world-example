package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.FotoProdutoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.FotoProduto;
import br.algaworks.Iago.Food.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo){
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeArquivoExistente = null;

        // Antes de Salvar a foto geramos um novo nome de arquivo
        String novoNomeArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());

        // Excluir foto se existir
        Optional<FotoProduto> fotoExistente =
                produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();

            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(novoNomeArquivo);
        // A foto é salva no bd antes, porque caso dê erro,
        // o banco de dados irá fazer rollback
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto =
                FotoStorageService.NovaFoto.builder()
                    .nomeArquivo(foto.getNomeArquivo())
                    .contentType(foto.getContentType())
                    .inputStream(dadosArquivo).build();


        // Método para armazenar a foto local em disco ou na nuvem
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() ->
                new FotoProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscar(restauranteId, produtoId);
        produtoRepository.delete(foto);
        produtoRepository.flush();

        fotoStorage.remover(foto.getNomeArquivo());
    }
}
