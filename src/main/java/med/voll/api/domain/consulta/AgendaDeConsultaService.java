package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {
    @Autowired
   private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    public void agendar(DatosAgendarConsulta datos){
        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }
        if(datos.idMedico()!=null&&medicoRepository.existsById(datos.idMedico())){

            throw new ValidacionDeIntegridad("este id ni fue encontrado");
        }
        //validaciones

        var paciente=pacienteRepository.findById(datos.idPaciente()).get();

        var medico =seleccionarMedico(datos);
        var consulta = new Consulta(null,paciente,medico,datos.fecha());
        consultaRepository.save(consulta);


    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {

        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());

        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionar una especialidad");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadFecha(datos.especialidad(),datos.fecha());
    }
}
