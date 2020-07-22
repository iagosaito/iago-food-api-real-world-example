package br.algaworks.Iago.Food.infrastructure.service.report;

import br.algaworks.Iago.Food.domain.filter.VendaDiariaFilter;
import br.algaworks.Iago.Food.domain.model.dto.VendaDiaria;
import br.algaworks.Iago.Food.domain.service.VendaQueryService;
import br.algaworks.Iago.Food.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class JasperVendaReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        try {
            // InputStream -> Fluxo de dados de um arquivo dentro do projeto
            var inputStream = this.getClass().getResourceAsStream(
                    "/relatorios/vendas_diarias.jasper");

            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "br"));

            List<VendaDiaria> vendasDiarias = vendaQueryService.consultarVendasDiarias(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            // JasperFillManager -> Gerente de preenchimento de relatórios
            // JasperPrint representa o relatório preenchido
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatórios de vendas diárias", e);
        }

    }
}
