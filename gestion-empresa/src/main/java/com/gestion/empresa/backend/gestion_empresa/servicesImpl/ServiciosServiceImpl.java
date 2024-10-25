package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.DevolverTodoServiciosDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.NuevoServicioDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.*;
import com.gestion.empresa.backend.gestion_empresa.services.ServiciosService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Author: alexxus
 * Created on: 15/10/24
 */

@Service
@Transactional(rollbackOn = Throwable.class)
public class ServiciosServiceImpl implements ServiciosService {

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private JornadaLaboralRepository jornadaLaboralRepository;

    @Autowired
    private DiasLaboralesRepository diasLaboralesRepository;

    @Autowired
    private JornadaPorDiaRepository jornadaPorDiaRepository;
    @Autowired
    private EstadoServicioRepository estadoServicioRepository;
    @Autowired
    private CategoriaServicioRepository categoriaServicioRepository;

    @Autowired
    private JornadaServicioRepository jornadaServicioRepository;

    @Autowired
    private ServicioPrestadoRepository servicioPrestadoRepository;


    @Autowired
    private DuracionServicioPrestadoRepository duracionServicioPrestadoRepository;

    @Autowired
    private ServiciosAsignadoRepository serviciosAsignadoRepository;


    @Autowired
    private ImagenServicioPrestadoRepository imagenServicioPrestadoRepository;
    @Autowired
    private ServicioPorUsuariosRepository servicioPorUsuariosRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Servicios crearServicio(Servicios servicios) {
        return  this.serviciosRepository.save(servicios);

    }

    @Override
    public List<Servicios> obtenerTodosServicios() {
        return this.serviciosRepository.findAll();
    }


    //  @Transactional(rollbackOn = Throwable.class)
    public ResponseBackend registroServicio(NuevoServicioDTO nuevoServicioDTO) {

        try {

            // Crear y guardar el nuevo servicio
            Servicios nuevoServicio = new Servicios();
            nuevoServicio.setNombre(nuevoServicioDTO.getServicios().getNombre());
            nuevoServicio.setDescripcion(nuevoServicioDTO.getServicios().getDescripcion());
            nuevoServicio.setImagen(nuevoServicioDTO.getServicios().getImagen());
            nuevoServicio.setIdEstadoServicio( estadoServicioRepository.findById(nuevoServicioDTO.getServicios().getIdEstadoServicio().getId())
                    .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado")));
            nuevoServicio.setIdTipoServicio( categoriaServicioRepository.findById(nuevoServicioDTO.getServicios().getIdTipoServicio().getId())
                    .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado")));

            Servicios servicioGuardado = serviciosRepository.save(nuevoServicio);
            // una vez creado el servicio creamos la unionentre los usuarios
            ServicioPorUsuarios nuevoServicioPorUsuarios = new ServicioPorUsuarios();
            System.out.println(nuevoServicioDTO.getIdUsuario());
            nuevoServicioPorUsuarios.setIdUsuario( usuarioRepository.findById(nuevoServicioDTO.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado")));
            nuevoServicioPorUsuarios.setIdServicio(servicioGuardado);
            ServicioPorUsuarios servicioPorUsuariosGuardado = servicioPorUsuariosRepository.save(nuevoServicioPorUsuarios);



            System.out.println(nuevoServicio);
            System.out.println(nuevoServicioDTO);

            // Guardar las jornadas laborales
            List<JornadaLaboral> jornadasLaboralesGuardadas = new ArrayList<>();
            for (JornadaLaboral jornadaDTO : nuevoServicioDTO.getJornadaLaboral()) {
                System.out.println(jornadaDTO);
                JornadaLaboral jornadaLaboral = new JornadaLaboral();
                jornadaLaboral.setId(jornadaDTO.getId());
                jornadaLaboral.setNombre(jornadaDTO.getNombre());
                jornadaLaboral.setHoraInicio(jornadaDTO.getHoraInicio());
                jornadaLaboral.setHoraFin(jornadaDTO.getHoraFin());

                // Relacionar con el servicio y guardar cada jornada
                JornadaLaboral jornadaGuardada = jornadaLaboralRepository.save(jornadaLaboral);
                jornadasLaboralesGuardadas.add(jornadaGuardada); // Guardar la jornada laboral para uso posterior
            }

            // Guardar los días laborales y asignarles las jornadas correspondientes
            System.out.println(jornadasLaboralesGuardadas);
            for (int i = 0; i < nuevoServicioDTO.getDiasLaborales().size(); i++) {
                DiasLaborales diasDTO = nuevoServicioDTO.getDiasLaborales().get(i);

                if (diasDTO != null && i < jornadasLaboralesGuardadas.size()) {

                    DiasLaborales diaLaboral = new DiasLaborales();
                    diaLaboral.setId(diasDTO.getId());
                    diaLaboral.setNombre(diasDTO.getNombre());

                    JornadaPorDia jornadaPorDia = new JornadaPorDia();
                    jornadaPorDia.setIdDiaLaboral(diaLaboral);

                    jornadaPorDia.setIdJornadaLaboral(jornadasLaboralesGuardadas.get(i));

                    JornadaPorDia guardadoJornadaDia = jornadaPorDiaRepository.save(jornadaPorDia);
                    // despues de crear la jornadas por dia entonces se otbneien el id y se crea el jorndaServicio

                    JornadaServicio nuevaJornadaServicio = new JornadaServicio();
                    nuevaJornadaServicio.setIdJornadaDia(guardadoJornadaDia);
                    nuevaJornadaServicio.setIdServicio(servicioGuardado);
                    jornadaServicioRepository.save(nuevaJornadaServicio);


            }
            }

            // aca debera de generar los servicios especificos que puede hacer

            List<ServicioPrestado> serviciosEspecificos = new ArrayList<>();
            for (ServicioPrestado servicios : nuevoServicioDTO.getServicioPrestados()) {
                System.out.println(servicios);
                ServicioPrestado servicioPrestado = new ServicioPrestado();
                servicioPrestado.setNombre(servicios.getNombre());
                servicioPrestado.setPrecio(servicios.getPrecio());
                servicioPrestado.setIdEstadoServicio(estadoServicioRepository.findById(servicios.getIdEstadoServicio().getId())
                        .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado")));


                System.out.println(servicioPrestado);
                // Relacionar con el servicio y guardar cada jornada
                ServicioPrestado serivicioFinalGuardado = servicioPrestadoRepository.save(servicioPrestado);
                serviciosEspecificos.add(serivicioFinalGuardado);
                //union para el servicio genral y los especificos

                ServiciosAsignado nuevoServiciosAsignado = new ServiciosAsignado();
                nuevoServiciosAsignado.setIdServicio(servicioGuardado);
                nuevoServiciosAsignado.setIdServicioPrestado(serivicioFinalGuardado);

                serviciosAsignadoRepository.save(nuevoServiciosAsignado);

            }


            for (int i = 0; i < nuevoServicioDTO.getDuracionServicioPrestados().size(); i++) {
                DuracionServicioPrestado duracionIndividual = nuevoServicioDTO.getDuracionServicioPrestados().get(i);

                DuracionServicioPrestado nuevaDuracionServicioPrestado = new DuracionServicioPrestado();
                nuevaDuracionServicioPrestado.setNombre(duracionIndividual.getNombre());
                nuevaDuracionServicioPrestado.setDuracion(duracionIndividual.getDuracion());
                nuevaDuracionServicioPrestado.setIdServicioPrestado(serviciosEspecificos.get(i));
                duracionServicioPrestadoRepository.save(nuevaDuracionServicioPrestado);


            }

            //aca se generan las imagenes
//            for (int i = 0; i < nuevoServicioDTO.getImagenServicioPrestados().size(); i++) {
//                ImagenServicioPrestado imagenEspecifica = nuevoServicioDTO.getImagenServicioPrestados().get(i);
//
//                ImagenServicioPrestado nuevaImagenServicioPrestado = new ImagenServicioPrestado();
//                nuevaImagenServicioPrestado.setNombreImagen(imagenEspecifica.getNombreImagen());
//                nuevaImagenServicioPrestado.setIdServicioPrestado(
//
//                        imagenServicioPrestadoRepository.findById(servicios.getIdEstadoServicio().getId())
//                                .orElseThrow(() -> new RuntimeException("El estado no se encuentra registrado"))
//                        imagenEspecifica.getIdServicioPrestado());
//                imagenServicioPrestadoRepository.save(nuevaImagenServicioPrestado);
//
//
//            }
            // Retornar éxito
            return new ResponseBackend(true, HttpStatus.CREATED, "Servicio registrado exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, la transacción se revertirá automáticamente
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar servicio: " + e.getMessage());
        }
    }


    // funcion de implementacion de obtener todos los servicios
    public DevolverTodoServiciosDTO obtenerServicios() {

        try {
            // Obtén los datos de los servicios
            List<Servicios> servicios = serviciosRepository.findAll();

            // Obtén las jornadas de servicio
            List<JornadaServicio> jornadaServicios = jornadaServicioRepository.findAll();

            // Para cada JornadaServicio, desglosamos la información
            List<DevolverTodoServiciosDTO.JornadaDTO> jornadasDTO = jornadaServicios.stream()
                    .map(jornadaServicio -> {
                        Servicios servicioDeterminado = jornadaServicio.getIdServicio();
                        JornadaPorDia jornadaPorDia = jornadaServicio.getIdJornadaDia();
                        JornadaLaboral jornadaLaboral = jornadaPorDia.getIdJornadaLaboral();
                        DiasLaborales diasLaborales = jornadaPorDia.getIdDiaLaboral();

                        // Obtener detalles del servicio
                        Servicios servicio = jornadaServicio.getIdServicio();

                        List<DuracionServicioPrestado> duracionServicioPrestados = duracionServicioPrestadoRepository.findAll();

                        List<DevolverTodoServiciosDTO.ServicioDTO> servicioDto = duracionServicioPrestados.stream().map(
                                servicioPrestadoLista -> {
                                    // Buscar el ServicioPrestado por su ID (aunque ya lo tienes en la lista, esta búsqueda podría no ser necesaria)
                                    Optional<DuracionServicioPrestado> duracionServicioPrestadoOptional = duracionServicioPrestadoRepository.findById(servicioPrestadoLista.getId());

                                    // Si el servicio prestado está presente, continuamos
                                    if (duracionServicioPrestadoOptional.isPresent()) {
                                        DuracionServicioPrestado duracionServicioPrestado = duracionServicioPrestadoOptional.get();

                                        // Buscar el ServiciosAsignado relacionado con el servicio prestado
                                        Optional<ServiciosAsignado> serviciosAsignadoOpt = serviciosAsignadoRepository.findByidServicioPrestado(duracionServicioPrestado.getIdServicioPrestado());

                                        // Si el serviciosAsignado está presente, continuamos
                                        if (serviciosAsignadoOpt.isPresent()) {
                                            ServiciosAsignado serviciosAsignado = serviciosAsignadoOpt.get();

                                            // Crear el DTO de Servicio
                                            return new DevolverTodoServiciosDTO.ServicioDTO(
                                                    duracionServicioPrestado                                                                 ,  // El objeto ServicioPrestado
                                                    serviciosAsignado  // El objeto ServiciosAsignado
                                            );
                                        } else {
                                            // Retornar null o manejar la ausencia del ServiciosAsignado según sea necesario
                                            return null;
                                        }
                                    } else {
                                        // Retornar null o manejar la ausencia del ServicioPrestado según sea necesario
                                        return null;
                                    }
                                }
                        ).collect(Collectors.toList());

                        // Creamos el DTO para Jornada
                        return new DevolverTodoServiciosDTO.JornadaDTO(
                                servicio, servicioDto.isEmpty() ? null : servicioDto.get(0), jornadaPorDia, jornadaLaboral, diasLaborales
                        );
                    })
                    .collect(Collectors.toList());

            // Poblar el DTO con los datos
            DevolverTodoServiciosDTO nuevoServicioDTO = new DevolverTodoServiciosDTO();
            nuevoServicioDTO.setJornadaServicio(jornadasDTO);  // Asignamos la lista de jornadas desglosadas

            // Otros datos relacionados, como Duración e Imágenes, pueden ser agregados de manera similar
            List<DuracionServicioPrestado> duracionServicioPrestados = duracionServicioPrestadoRepository.findAll();
            List<ImagenServicioPrestado> imagenServicioPrestados = imagenServicioPrestadoRepository.findAll();

            nuevoServicioDTO.setDuracionServicioPrestados(duracionServicioPrestados);
            nuevoServicioDTO.setImagenServicioPrestados(imagenServicioPrestados);

            return nuevoServicioDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
