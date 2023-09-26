package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.HorarioDeAnticipacion;
import med.voll.api.domain.consulta.validaciones.ValIdadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    @Autowired
   private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    List<ValIdadorDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }
        if(datos.idMedico()!=null&& !medicoRepository.existsById(datos.idMedico())){

            throw new ValidacionDeIntegridad("este id ni fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));

        var paciente=pacienteRepository.findById(datos.idPaciente()).get();

        var medico =seleccionarMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridad("No existen medicos disponible para este horario");
        }

        var consulta = new Consulta(medico,paciente,datos.fecha());
        consultaRepository.save(consulta);


        return new DatosDetalleConsulta(consulta);


    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {

        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());

        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionar una especialidad");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
