
package logica;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.mindrot.jbcrypt.BCrypt;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    private String nombre_usu;
    private String apellido_usu;
    private String telefono;
    private String user;
    private String password;
    private String rol;
    
    @OneToMany(mappedBy="user")
    private List<Meta> metas;

    public User() {
    }

    public User(int id_usuario, String nombre_usu, String apellido_usu, String telefono, String user, String password, String rol, List<Meta> metas) {
        this.id_usuario = id_usuario;
        this.nombre_usu = nombre_usu;
        this.apellido_usu = apellido_usu;
        this.telefono = telefono;
        this.user = user;
        this.password = password;
        this.rol = rol;
        this.metas = metas;
    }

     
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usu() {
        return nombre_usu;
    }

    public void setNombre_usu(String nombre_usu) {
        this.nombre_usu = nombre_usu;
    }

    public String getApellido_usu() {
        return apellido_usu;
    }

    public void setApellido_usu(String apellido_usu) {
        this.apellido_usu = apellido_usu;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    // Método para verificar la contraseña
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}
