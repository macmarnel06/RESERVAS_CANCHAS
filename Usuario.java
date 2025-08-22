public class Usuario {
    private String nombre;
    private String estab_tipo;
    private String email;
    private String telefono;
    
    public Usuario(String nombre, String estab_tipo, String email, String telefono) {
        this.nombre = nombre;
        this.estab_tipo = estab_tipo;
        this.email = email;
        this.telefono = telefono;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEstabTipo() {
        return estab_tipo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setEstabTipo(String estab_tipo) {
        this.estab_tipo = estab_tipo;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public String toString() {
        return "Usuario: " + nombre + " (" + estab_tipo + ") - " + email + " - " + telefono;
    }
    
    public boolean esOrganizador() {
        return "organizador".equalsIgnoreCase(estab_tipo);
    }
    
    public boolean esEmpleado() {
        return "empleado".equalsIgnoreCase(estab_tipo);
    }
}