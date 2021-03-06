package SistemaAgil_IS2.model;

public class UsuarioRol {
    private Integer usuarioId;
    private Integer roleId;

    public UsuarioRol(Integer usuarioId, Integer roleId) {
        this.usuarioId = usuarioId;
        this.roleId = roleId;
    }

    public UsuarioRol() {
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UsuarioRol{" +
                "usuarioId=" + usuarioId +
                ", roleId=" + roleId +
                '}';
    }
}
