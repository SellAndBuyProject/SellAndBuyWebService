package com.sanvalero.SellAndBuy.service;

import com.sanvalero.SellAndBuy.domain.Usuario;

import java.util.Set;

public interface UsuarioService {

    Set<Usuario> findAll();
    Usuario findByName(String name);
    Usuario findByEmailAndPassword(String email, String password);
    Usuario addUsuario(Usuario usuario);
    Usuario modifyNombre(int id, String newNombre);
    Usuario changePass(int usuarioId, String oldPassword, String newPassword);
    void deleteUsuario(int usuarioId, String password);
}
