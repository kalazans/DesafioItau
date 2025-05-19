package br.com.desafioItau.Itau.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


import java.time.OffsetDateTime;
@JsonIgnoreProperties(ignoreUnknown = false)
public record NovaTransacaoDTO(@NotNull Float valor,
                               @NotNull @PastOrPresent  OffsetDateTime dataHora) {
}
