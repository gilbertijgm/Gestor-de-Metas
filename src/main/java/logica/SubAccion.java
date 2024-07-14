package logica;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 

@Entity
public class SubAccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_subAccion;
    private String nombre_sub;
    private boolean completada;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_accion")
    private Accion accion;

    public SubAccion() {
    }

    public SubAccion(int id_subAccion, String nombre_sub, boolean completada, Date fecha, Accion accion) {
        this.id_subAccion = id_subAccion;
        this.nombre_sub = nombre_sub;
        this.completada = completada;
        this.fecha = fecha;
        this.accion = accion;
    }


    public int getId_subAccion() {
        return id_subAccion;
    }

    public void setId_subAccion(int id_subAccion) {
        this.id_subAccion = id_subAccion;
    }

    public String getNombre_sub() {
        return nombre_sub;
    }

    public void setNombre_sub(String nombre_sub) {
        this.nombre_sub = nombre_sub;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    
}

