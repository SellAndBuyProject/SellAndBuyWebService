package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Usuario;
import com.sanvalero.SellAndBuy.exception.UnauthorizedException;
import com.sanvalero.SellAndBuy.exception.UsuarioDuplicateException;
import com.sanvalero.SellAndBuy.exception.UsuarioMissingDataException;
import com.sanvalero.SellAndBuy.exception.UsuarioNotFoundException;
import com.sanvalero.SellAndBuy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @version Curso 2020-2021
 * @author: veronica
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Servicio que busca todos los usuarios.
     * @return Set de objetos de tipo Usuario.
     */
    @Override
    public Set<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Servicio que busca usuarios por nombre.
     * @param name nombre del usuario que se desea buscar.
     * @return objeto usuario.
     */
    @Override
    public Usuario findByName(String name) {
        return usuarioRepository.findByName(name);
    }

    /**
     * Servicio que busca usuarios por email y contraseña para hacer el login.
     * @param email email del usuario.
     * @param password contraseña del usuario.
     * @return objeto usuario.
     */
    @Override
    public Usuario findByEmailAndPassword(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    /**
     * Servicio que registra un usuario.
     * @param usuario Objeto usuario.
     * @return objeto usuario creado.
     */
    @Override
    public Usuario addUsuario(Usuario usuario) {
        if (usuarioRepository.existsByNombre(usuario.getNombre()))
            throw new UsuarioDuplicateException(usuario.getNombre());

        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new UsuarioDuplicateException(usuario.getEmail());

        if (usuario.getNombre().isEmpty() || usuario.getEmail().isEmpty() || usuario.getPassword().isEmpty())
            throw new UsuarioMissingDataException();
        return usuarioRepository.save(usuario);
    }

    /**
     * Servicio que permite al usuario modificar su nombre.
     * @param id identificador del usuario.
     * @param newNombre nuevo nombre que le quiere poner.
     * @return objeto usuario.
     */
    @Override
    public Usuario modifyNombre(int id, String newNombre) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        usuario.setNombre(newNombre);
        return usuarioRepository.save(usuario);
    }

    /**
     * Servicio que permite al usuario modificar su contraseña.
     * @param id identificador del usuario.
     * @param oldPassword contraseña anterior.
     * @param newPassword nueva contraseña.
     * @return objeto usuario.
     */
    @Override
    public Usuario changePass(int id, String oldPassword, String newPassword) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!usuario.getPassword().equals(oldPassword))
            throw new UnauthorizedException();

        usuario.setPassword(newPassword);
        return usuarioRepository.save(usuario);
    }

    /**
     * Servicio que permite eliminar un usuario.
     * @param id identificador del usuario.
     * @param password contraseña del usuario.
     */
    @Override
    public void deleteUsuario(int id, String password) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!usuario.getPassword().equals(password))
            throw new UnauthorizedException();

        usuarioRepository.delete(usuario);
    }
}
