package ec.edu.uce.servicios;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.Funcion;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.repositorios.UsuarioRepositorio;

public class UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;
    private Context context;

    public UsuarioServicio(Context context) {
        this.context = context;
        this.usuarioRepositorio = new UsuarioRepositorio(context);
    }

    public void crear(Usuario usuario) throws CustomException{
        if (buscarPorUsuario(usuario.getUsuario()) == null) {
            String mensaje = usuarioRepositorio.crear(usuario);
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        } else {
            throw new CustomException("Ya existe un usuario con el nombre " + usuario.getUsuario());
        }
    }

    public boolean validar(String usuario, String clave) {
        Usuario u = buscarPorUsuario(usuario);

        if (u != null) {
            return u.getCalve().equals(clave);
        } else {
            return false;
        }
    }

    public Usuario buscarPorUsuario(String usuario) {
        List<Usuario> usuarios = usuarioRepositorio.buscarPorParametro(new Funcion<Usuario, String>() {
            @Override
            public String apply(Usuario input) {
                return input.getUsuario();
            }
        }, usuario);

        return usuarios.isEmpty()? null : usuarios.get(0);
    }

    public void close() {
        usuarioRepositorio.close();
    }
}
