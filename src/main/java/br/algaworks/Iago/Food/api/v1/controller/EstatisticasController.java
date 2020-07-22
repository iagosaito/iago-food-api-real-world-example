package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.filter.VendaDiariaFilter;
import br.algaworks.Iago.Food.domain.model.dto.VendaDiaria;
import br.algaworks.Iago.Food.domain.service.VendaQueryService;
import br.algaworks.Iago.Food.infrastructure.service.report.JasperVendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private ApiLinks links;

    @Autowired
    private JasperVendaReportService vendaReportService;

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping
    public EstatisticaModel estatisticas() {
        EstatisticaModel estatisticaModel = new EstatisticaModel();

        estatisticaModel.add(links.linkToEstatisticasVendaDiarias("vendas-diarias"));

        return estatisticaModel;
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @Override
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filter, timeOffset);
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @Override
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

    private static class EstatisticaModel extends RepresentationModel<EstatisticaModel>{

    }
}
