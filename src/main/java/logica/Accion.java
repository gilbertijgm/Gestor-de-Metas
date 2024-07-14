package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Accion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_accion;
    private String nombre;
    private double porcentajeCumplimiento;

    @ManyToOne
    @JoinColumn(name = "id_meta")
    private Meta metas;

    @OneToMany(mappedBy = "accion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubAccion> subacciones;

    public Accion() {
    }

    public Accion(int id_accion, String nombre, double porcentajeCumplimiento, Meta metas, List<SubAccion> subacciones) {
        this.id_accion = id_accion;
        this.nombre = nombre;
        this.porcentajeCumplimiento = porcentajeCumplimiento;
        this.metas = metas;
        this.subacciones = subacciones;
    }

    public int getId_accion() {
        return id_accion;
    }

    public void setId_accion(int id_accion) {
        this.id_accion = id_accion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(double porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    public Meta getMetas() {
        return metas;
    }

    public void setMetas(Meta metas) {
        this.metas = metas;
    }

    public List<SubAccion> getSubacciones() {
        return subacciones;
    }

    public void setSubacciones(List<SubAccion> subacciones) {
        this.subacciones = subacciones;
    }

}
