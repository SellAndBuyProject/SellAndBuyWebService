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

    @Override
    public Set<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findByEmailAndPassword(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    @Override
    public Usuario addUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new UsuarioDuplicateException(usuario.getEmail());

        if (usuario.getNombre().isEmpty() || usuario.getEmail().isEmpty() || usuario.getPassword().isEmpty())
            throw new UsuarioMissingDataException();
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario modifyNombre(int id, String newNombre) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        usuario.setNombre(newNombre);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario changePass(int id, String oldPassword, String newPassword) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!usuario.getPassword().equals(oldPassword))
            throw new UnauthorizedException();

        usuario.setPassword(newPassword);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(int id, String password) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!usuario.getPassword().equals(password))
            throw new UnauthorizedException();

        usuarioRepository.delete(usuario);
    }
}
