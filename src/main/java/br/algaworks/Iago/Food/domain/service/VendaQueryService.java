package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.filter.VendaDiariaFilter;
import br.algaworks.Iago.Food.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);

}
