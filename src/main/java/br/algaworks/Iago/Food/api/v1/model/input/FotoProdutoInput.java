package br.algaworks.Iago.Food.api.v1.model.input;

import br.algaworks.Iago.Food.core.validation.FileContentType;
import br.algaworks.Iago.Food.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @ApiModelProperty(hidden = true)
    @NotNull
    @FileSize(maxSize = "900KB")
    @FileContentType(allowed = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    private MultipartFile arquivo;

    @ApiModelProperty(example = "Descrição da foto do produto", required = true)
    @NotBlank
    private String descricao;
}
