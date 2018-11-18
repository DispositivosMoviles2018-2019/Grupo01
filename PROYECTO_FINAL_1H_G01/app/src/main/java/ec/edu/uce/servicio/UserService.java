package ec.edu.uce.servicio;

import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.StorageException;
import ec.edu.uce.modelo.Usuario;

public class UserService {

    private ObjectMapper MAPPER = new ObjectMapper();
    private final String REGISTER_FILE_NAME = "usuarios.txt";

    private FileManagerService fileManagerService = new FileManagerService();
    private File registerFile;

    public UserService() {
        this.registerFile = fileManagerService.getFile(REGISTER_FILE_NAME);
    }

    public void createUsersFileIfNotExist(Context context) {
        try {
            if (!existFile()) {
                fileManagerService.createFile(REGISTER_FILE_NAME);
                Toast.makeText(context, "Creando el archivo: " + REGISTER_FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (StorageException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public Usuario save(Usuario usuario) {
        List<Usuario> usuarios = getUsuarios();
        usuarios.add(usuario);
        try {
            fileManagerService.writeFile(registerFile, MAPPER.writeValueAsString(usuarios));
            return usuario;
        } catch (JsonProcessingException e) {
            throw new CustomException("Error al guardar los datos del usuarios", e);
        }
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        if (existFile()) {
            String registerData = fileManagerService.readFile(registerFile);
            if (!registerData.isEmpty()) {
                try {
                    usuarios = MAPPER.readValue(registerData, new TypeReference<List<Usuario>>() {
                    });
                } catch (IOException e) {
                    throw new CustomException("Error al leer el archivo: " + REGISTER_FILE_NAME, e);
                }
            }
        }
        return usuarios;
    }

    public Usuario findByUsername(String username) {
        List<Usuario> usuarios = getUsuarios();

        for (Usuario usr : usuarios) {
            if (usr.getUsername().equals(username)) {
                return usr;
            }
        }
        return null;
    }

    public boolean existUsuario(Usuario usuario) {
        return findByUsername(usuario.getUsername()) != null;
    }

    public boolean existFile() {
        return fileManagerService.existFile(REGISTER_FILE_NAME);
    }
}
