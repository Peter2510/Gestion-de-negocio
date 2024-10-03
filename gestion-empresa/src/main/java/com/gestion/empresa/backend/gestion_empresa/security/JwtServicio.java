package com.gestion.empresa.backend.gestion_empresa.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// se determina la clase como servicio
@Service
public class JwtServicio {

    @Value("${security.jwt.secret-key}")

    private String llave;

    @Value("${security.jwt.secret-expiration-time}")
    private long jwtExpiracion;



// extare el nombre de usuario 7

    public String extarerNombreUsuario (String token){
            return  extraerElementos(token, Claims::getSubject);
    }

    public <T> T extraerElementos(String token, Function<Claims, T> resolucionClaims){
        final Claims claims = extraerTodasLasClaims(token);
        return  resolucionClaims.apply(claims);

    }

    //para generar

    public String generarToken(UserDetails userDetails){
    return  generarToken(new HashMap<>(), userDetails);
    }

    public String generarToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return construirToken(extraClaims, userDetails, jwtExpiracion);
    }


    private String construirToken(Map<String, Object> extraClaims, UserDetails userDetails, Long tiempoExpiracion)
    {
    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+tiempoExpiracion))
            .signWith(getLlaveIngreso(), SignatureAlgorithm.ES256)
            .compact();
    }


    //fucnion para ovtener el tiempo de expirtacion

    public Long ObtenerTiempoExpiracion(){
        return  jwtExpiracion;
    }


    // para verificar que si sea valido

    public boolean esValido(String token, UserDetails userDetails){
        final String cadena =extarerNombreUsuario(token);
        return (cadena.equals(userDetails.getUsername()) && !TokenExpirado(token));

    }


    public boolean TokenExpirado(String token){
        return ObtenerExpiracion(token).before(new Date());
    }


    public Date ObtenerExpiracion(String token) {
    return extraerElementos(token, Claims::getExpiration);
    }



    private Claims extraerTodasLasClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getLlaveIngreso())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getLlaveIngreso() {
        byte[] keyBytes = Decoders.BASE64.decode(llave);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
