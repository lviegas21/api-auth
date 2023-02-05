package com.api.autenticacao.controller;

import com.api.autenticacao.DTO.*;
import com.api.autenticacao.exception.SenhaInvalidaException;
import com.api.autenticacao.service.UsuarioService;
import com.api.autenticacao.DTO.CredenciaisDTO;
import lombok.RequiredArgsConstructor;
import com.api.autenticacao.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.autenticacao.entity.Usuario;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping("/login")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {

            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            System.out.println(credenciais);

            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            System.out.println(usuarioAutenticado);

            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }


    }
}
