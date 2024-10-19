package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.dto.NuevoServicioDTO;
import com.gestion.empresa.backend.gestion_empresa.dto.RegistroUsuariosDTO;
import com.gestion.empresa.backend.gestion_empresa.models.*;
import com.gestion.empresa.backend.gestion_empresa.repositories.*;
import com.gestion.empresa.backend.gestion_empresa.services.CategoriaServicioService;
import com.gestion.empresa.backend.gestion_empresa.services.ServiciosService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Servicios crearServicio(Servicios servicios) {
        return  this.serviciosRepository.save(servicios);

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

}
