 
package logica;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Meta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_meta;
    private String nombre_meta;
    private String tipo;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;   
    private Date fechaLimite;
    private double porcentajeCumplimiento;

    @ManyToOne
    @JoinColumn(name = "id_usuario") // Nombre de la columna en la tabla Meta
    private User user;
    
    @OneToMany(mappedBy = "metas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accion> acciones;

    public Meta() {
    }

//    public Meta(int id_meta, String nombre_meta, String tipo, Date fechaLimite, double porcentajeCumplimiento, User user, List<Accion> acciones) {
//        this.id_meta = id_meta;
//        this.nombre_meta = nombre_meta;
//        this.tipo = tipo;
//        this.fechaLimite = fechaLimite;
//        this.porcentajeCumplimiento = porcentajeCumplimiento;
//        this.user = user;
//        this.acciones = acciones;
//    }

    public Meta(int id_meta, String nombre_meta, String tipo, Date fechaInicio, Date fechaLimite, double porcentajeCumplimiento, User user, List<Accion> acciones) {
        this.id_meta = id_meta;
        this.nombre_meta = nombre_meta;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaLimite = fechaLimite;
        this.porcentajeCumplimiento = porcentajeCumplimiento;
        this.user = user;
        this.acciones = acciones;
    }

    

    public int getId_meta() {
        return id_meta;
    }

    public void setId_meta(int id_meta) {
        this.id_meta = id_meta;
    }

    public String getNombre_meta() {
        return nombre_meta;
    }

    public void setNombre_meta(String nombre_meta) {
        this.nombre_meta = nombre_meta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public double getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(double porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    public List<Accion> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<Accion> acciones) {
        this.acciones = acciones;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

}