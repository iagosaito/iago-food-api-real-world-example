package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.filter.VendaDiariaFilter;
import br.algaworks.Iago.Food.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaReportService {
    byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset);
}
