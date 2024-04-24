public class Usuario {
    private String nombreUsuario;
    private String contraUsuario;
    //private String dataCreacion;

    public Usuario(String nombreUsuario, String contraUsuario){
        this.nombreUsuario = nombreUsuario;
        this.contraUsuario = contraUsuario;
        //this.dataCreacion = "NULL";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)                return false;
        if (!(obj instanceof Usuario))  return false;
        if (obj == this)                return true;
        Usuario userObj = (Usuario) obj;
        return userObj.nombreUsuario.equals(this.nombreUsuario);
    }

    @Override
    public String toString() {
        return this.nombreUsuario + "(" + "24/04/2024" + ")";
    }
}