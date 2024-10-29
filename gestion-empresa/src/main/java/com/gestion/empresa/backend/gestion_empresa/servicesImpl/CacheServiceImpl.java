package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Author: gordillox
 * Created on: 29/10/24
 */
@Service
public class CacheServiceImpl {


    //se almacena el código en caché con el correo como clave
    @CachePut(value = "myCache", key = "#email")
    public String almacenarCodigoEnCache(String email, String codigo) {
        return codigo;
    }

    //esto recupera el codigo desde el caché usando el correo como clave
    @Cacheable(value = "myCache", key = "#email")
    public String recuperarCodigoDelCache(String email) {
        return null;  //spring devolverá el valor en caché si existe si no tira un null
    }

    //para limpiar el caché después de validar el código
    @CacheEvict(value = "myCache", key = "#email")
    public void limpiarCodigoDelCache(String email) {
    }

}
