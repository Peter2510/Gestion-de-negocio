package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.AuthRespuesta;
import com.gestion.empresa.backend.gestion_empresa.dto.Login;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuariosDTO;
import com.gestion.empresa.backend.gestion_empresa.models.Genero;
import com.gestion.empresa.backend.gestion_empresa.models.Persona;
import com.gestion.empresa.backend.gestion_empresa.models.Rol;
import com.gestion.empresa.backend.gestion_empresa.models.Usuarios;
import com.gestion.empresa.backend.gestion_empresa.repositories.GeneroRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.PersonaRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.RolRepository;
import com.gestion.empresa.backend.gestion_empresa.repositories.UsuarioRepository;
import com.gestion.empresa.backend.gestion_empresa.security.JwtServicio;
import com.gestion.empresa.backend.gestion_empresa.services.AutenticacionServicie;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionServicie {
    @Autowired
    private final UsuarioRepository userRepository;
    @Autowired
    private final PersonaRepository personaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServicio jwtServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CorreoMensajeServiceImpl emailService;

    @Autowired
    private CacheServiceImpl cacheService;

    @Autowired
    private CacheManager cacheManager;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codigo.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return codigo.toString();
    }


    @Transactional
    public Optional<AuthRespuesta> login(Login valor) {
        System.out.println(valor);

        // Buscar al usuario en la base de datos
        Optional<Usuarios> user = userRepository.findByNombreUsuario(valor.getNombreUsuario());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        // Comparar la contraseña ingresada con la contraseña encriptada en la base de datos
        if (!passwordEncoder.matches(valor.getPassword(), user.get().getPassword())) {
            return Optional.ofNullable(AuthRespuesta.builder().token("no coincide").build());
        }

        //validar a2f
        if(user.get().isA2fActivo()){

            String correo = user.get().getPersona().getCorreo();
            Long idUsuario = userRepository.findByNombreUsuario(valor.getNombreUsuario()).get().getId();

            System.out.println("id eel usuarios"+idUsuario);

            String codigoGenerado = generarCodigo();

            //almacenar el código en caché con el correo como clave
            String codigo = cacheService.almacenarCodigoEnCacheIdUsuario(idUsuario.toString(), codigoGenerado);

            //verificar si se almacenó correctamente
            Object cachedValue = cacheManager.getCache("myCache").get(idUsuario.toString(), String.class);
            System.out.println("Valor en caché para " + idUsuario + ": " + cachedValue);

            String cuerpo = "<p>Este es un <strong>correo para validar el ingreso su cuenta</strong>.</p>" +
                    "<p>Tu código de validacion es: <strong>" + codigo + "</strong></p>" +
                    "<p>Tiene validez de 10 minutos</p>";

            System.out.println("Código generado: " + codigo);
            ResponseBackend response = emailService.generarCorreo(correo, "Autenticacion doble factor", cuerpo);

            return Optional.ofNullable(AuthRespuesta.builder().token(response.getMensaje()).idUsuario(user.get().getId()).status(response.getStatus()).build());

        }

        // Si la autenticación es correcta, generar el token JWT
        String token = jwtServicio.obtenerToken(user.get());

        return Optional.ofNullable(AuthRespuesta.builder().token(token).build());
    }

    @Override
    public ResponseBackend validacionCodigoA2F(Long idUsuario, String codigo) {

        String  codigoAlmacenado = cacheService.recuperarCodigoDelCacheIdUsuario(idUsuario.toString());

        //valida si el código almacenado coincide con el código ingresado
        if (codigoAlmacenado != null && codigoAlmacenado.equals(codigo)) {
            cacheService.limpiarCodigoDelCacheidUsuario(idUsuario.toString());
            Optional<Usuarios> usuario = usuarioRepository.findById(idUsuario);

            String token = jwtServicio.obtenerToken(usuario.get());

            return new ResponseBackend(true, HttpStatus.OK, token);

        } else {
            return new ResponseBackend(false, HttpStatus.BAD_REQUEST, "Código invalido");
        }

    }

/*
*
*  @Override
    public ResponseBackend validarCodigoRecuperacion(String correo, String codigoRecuperacion) {
        String  codigoAlmacenado = cacheService.recuperarCodigoDelCache(correo);

        //valida si el código almacenado coincide con el código ingresado
        if (codigoAlmacenado != null && codigoAlmacenado.equals(codigoRecuperacion)) {
            cacheService.limpiarCodigoDelCache(correo);
            Optional<Persona> existenciaUsuario = personaServicio.buscarPorCorreo(correo);
            Optional<Usuarios> idUsuario = usuarioRepository.findByPersonaCui(existenciaUsuario.get().getCui());
            //retorna el id del usuario para cambiar la contraseña
            return new ResponseBackend(true, HttpStatus.OK, "Codigo válido", idUsuario.get().getId() );
        } else {
            return new ResponseBackend(false, HttpStatus.BAD_REQUEST, "Código invalido");
        }
    }

*
* */

    public Optional<AuthRespuesta> validarCredenciales(String password, Optional<Usuarios> usuarios) {
        if ((password == usuarios.get().getPassword())) {
            return (generacionToken(usuarios));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthRespuesta> generacionToken(Optional<Usuarios> usuarios) {
        String token = jwtServicio.obtenerToken(usuarios.get());

        return Optional.ofNullable(AuthRespuesta.builder().token(token).build());
    }

    @Transactional(rollbackOn = Throwable.class)
    public ResponseBackend registrarUsuario(RegistroUsuariosDTO registro) {

        //validaciones
        ResponseBackend validacionResponse = validarRegistro(registro);
        if (validacionResponse != null) {
            return validacionResponse;
        }

        try {
            //crear Persona
            Persona persona = new Persona();
            persona.setCui(registro.getPersona().getCui());
            persona.setNombre(registro.getPersona().getNombre());
            persona.setNit(registro.getPersona().getNit());
            persona.setDireccion(registro.getPersona().getDireccion());
            persona.setTelefono(registro.getPersona().getTelefono());
            persona.setCorreo(registro.getCorreo());
            persona.setGenero(generoRepository.findById(registro.getIdGenero())
                    .orElseThrow(() -> new RuntimeException("El genero no se encuentra registrado")));

            //guardar persona
            Persona nuevaPersona = personaRepository.save(persona);

            //crear Usuario
            Usuarios usuario = new Usuarios();
            usuario.setNombreUsuario(registro.getNombreUsuario());
            usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
            usuario.setRol(rolRepository.findById(registro.getIdRol())
                    .orElseThrow(() -> new RuntimeException("El rol no se encuentra registrado")));
            usuario.setPersona(nuevaPersona); // Cambia a nuevaPersona
            usuario.setA2fActivo(registro.isA2fActivo());
            usuario.setActivo(registro.isActivo());

            //guardar usuario
            usuarioRepository.save(usuario);

            //exito zzzzz
            return new ResponseBackend(true, HttpStatus.CREATED, "Usuario creado exitosamente");

        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            // La transacción se marca automáticamente para rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    public ResponseBackend validarRegistro(RegistroUsuariosDTO registro) {

        if (userRepository.findByNombreUsuario(registro.getNombreUsuario()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El nombre de usuario ya está en uso");
        }

        if (personaRepository.findByCorreo(registro.getCorreo()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El correo electrónico ya se encuentra registrado");
        }

        if (personaRepository.findByCui(registro.getPersona().getCui()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El CUI ya se encuentra registrado");
        }

        if (personaRepository.findByNit(registro.getPersona().getNit()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El NIT ya se encuentra registrado");
        }

        if (personaRepository.findByTelefono(registro.getPersona().getTelefono()).isPresent()) {
            return new ResponseBackend(false, HttpStatus.CONFLICT, "El teléfono ya se encuentra registrado");
        }

        return null;
    }

}



