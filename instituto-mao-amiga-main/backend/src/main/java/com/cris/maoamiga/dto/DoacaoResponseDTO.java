package com.cris.maoamiga.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DoacaoResponseDTO(UUID id, String tipoItem, Integer quantidade, String pontoDestino, LocalDateTime criadoEm) {
    public DoacaoResponseDTO(Doacao doacao) {
        this(doacao.getId(), doacao.getTipoItem(), doacao.getQuantidade(), doacao.getPontoDestino(), doacao.getCriadoEm());
    }
}
