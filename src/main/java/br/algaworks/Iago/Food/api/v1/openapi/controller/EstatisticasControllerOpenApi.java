package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.domain.filter.VendaDiariaFilter;
import br.algaworks.Iago.Food.domain.model.dto.VendaDiaria;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation(value = "Consulta as Vendas Diárias", produces = "application/json, application/pdf")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do Restaurante",
                example = "1", dataType = "1"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data de criação inicial",
                example = "2018-02-02T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data de criação final",
                    example = "2021-02-02T00:00:00Z", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
                                             @ApiParam(value = "Deslocamento da DataHora", example = "+03:00", defaultValue = "+00:00") String timeOffset);

    @ApiOperation(value = "Gera relatório de Vendas Diárias em PDF", hidden = true)
    ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filter, String timeOffset);
}
