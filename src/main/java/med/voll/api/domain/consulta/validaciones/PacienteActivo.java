package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo  implements ValIdadorDeConsultas{


    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar(DatosAgendarConsulta datos){

        if(datos.idPaciente()==null){
            return;
        }
        //inyectar consulta

         var pacienteActivo=pacienteRepository.findByActivoById(datos.idPaciente());

        if(!pacienteActivo){
            throw new ValidationException("No se puede permitir agendar ERROR PACIENTE INACTIVO");
        }

    }
}
