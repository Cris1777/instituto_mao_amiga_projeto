package com.cris.maoamiga.dto;

import java.util.UUID;

public record PontoResponseDTO(UUID id, String nome, String endereco, String bairro, String horario, String tipo) {
    public PontoResponseDTO(Ponto ponto) {
        this(ponto.getId(), ponto.getNome(), ponto.getEndereco(), ponto.getBairro(), ponto.getHorario(), ponto.getTipo().name());
    }
}
