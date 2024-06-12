 
package com.mycompany.concecionarioautos.persistencia;

import com.mycompany.concecionarioautos.logica.Automovil;
import com.mycompany.concecionarioautos.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

 
public class ControladorPersistencia {
    AutomovilJpaController automovilJpa = new AutomovilJpaController();

    public void guardar(Automovil auto) {
        automovilJpa.create(auto);
    }

    public List<Automovil> traerAutos() {
        return automovilJpa.findAutomovilEntities();
    }

    public void borrarAuto(int id) {
        try {
            automovilJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Automovil traerAuto(int id) {
        return automovilJpa.findAutomovil(id);
    }

    public void modificarAuto(Automovil auto) {
        try {
            automovilJpa.edit(auto);
        } catch (Exception ex) {
            Logger.getLogger(ControladorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
