package com.cris.maoamiga.dto;

import com.cris.maoamiga.entity.TipoPonto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontoRequestDTO(
        @NotBlank String nome,
        @NotBlank String endereco,
        @NotBlank String bairro,
        @NotBlank String horario,
        @NotNull TipoPonto tipo
) {}
