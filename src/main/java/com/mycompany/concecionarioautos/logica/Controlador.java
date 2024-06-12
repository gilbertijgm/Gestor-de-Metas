 
package com.mycompany.concecionarioautos.logica;

import com.mycompany.concecionarioautos.persistencia.ControladorPersistencia;
import java.util.List;

 
public class Controlador {
    ControladorPersistencia controlPersis = new ControladorPersistencia();

    public void guardar(String modelo, String marca, String motor, String color, String patente, int cantPuertas) {
        Automovil auto = new Automovil();
        
        auto.setModelo(modelo);
        auto.setMarca(marca);
        auto.setMotor(motor);
        auto.setColor(color);
        auto.setPatente(patente);
        auto.setCantidadPuertas(cantPuertas);
        
        controlPersis.guardar(auto);
                
    }

    public List<Automovil> traerAutomoviles() {
        return controlPersis.traerAutos();
    }

    public void borrarAuto(int id) {
        controlPersis.borrarAuto(id);
    }

    public Automovil traerAuto(int id) {
        return controlPersis.traerAuto(id);
    }

    public void modificarAuto(Automovil auto, String modelo, String marca, String motor, String color, String patente, int cantPuertas) {
        
        //setamos los nuevos datos al objeto viejo
        auto.setModelo(modelo);
        auto.setMarca(marca);
        auto.setMotor(motor);
        auto.setColor(color);
        auto.setPatente(patente);
        auto.setCantidadPuertas(cantPuertas);
        
        controlPersis.modificarAuto(auto);
    }

   

    
}
