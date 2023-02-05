package com.api.autenticacao.service;

import com.api.autenticacao.entity.Usuario;
import com.api.autenticacao.exception.SenhaInvalidaException;

import com.api.autenticacao.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));



        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles()
                .build();
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        System.out.println(user);

        boolean senhasBatem = encoder.matches( usuario.getSenha(), user.getPassword() );
        System.out.println(encoder.encode(usuario.getSenha()));
        System.out.println(encoder.encode(user.getPassword()));






        if(senhasBatem){
            return user;
        }

        throw new SenhaInvalidaException();
    }
}
