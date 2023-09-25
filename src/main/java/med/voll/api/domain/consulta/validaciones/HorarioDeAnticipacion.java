package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;


import java.time.Duration;
import java.time.LocalDateTime;



public class HorarioDeAnticipacion {
    public void validar (DatosAgendarConsulta datos){

        var ahora= LocalDateTime.now();
        var horaConsulta=datos.fecha();
        var diferenciaDe30min= Duration.between(ahora,horaConsulta).toMinutes()< 30;

        if(diferenciaDe30min ){
           throw new ValidationException("Las consultas deben programarse con 30 min de anticipacion");


        }


    }
}
