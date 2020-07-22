package br.algaworks.Iago.Food.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldMapping) {
        List<Sort.Order> orders = pageable.getSort().stream()
                .filter(order -> fieldMapping.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), fieldMapping.get(order.getProperty())))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
