package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        @NotNull Long id,
        @NotNull Long idPaciente,
        @NotNull Long idMedico, @NotNull @Future LocalDateTime fecha, Especialidad especialidad) {
}