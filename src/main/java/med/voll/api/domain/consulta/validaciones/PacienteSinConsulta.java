package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class PacienteSinConsulta {

    private PacienteRepository repository;
    private ConsultaRepository consultaRepository;
    public void validar(DatosAgendarConsulta datos){

        var primerHorario=datos.fecha().withHour(7);
        var ultimoHorario=datos.fecha().withHour(18);

        var pacienteConsulta=consultaRepository.existsByPacienteIdAndDataBetween(datos.idPaciente(),primerHorario,ultimoHorario);

        if(pacienteConsulta){
            throw new ValidationException("EL paciente ya tiene una consulta para ese dia");
        }
    }
}
