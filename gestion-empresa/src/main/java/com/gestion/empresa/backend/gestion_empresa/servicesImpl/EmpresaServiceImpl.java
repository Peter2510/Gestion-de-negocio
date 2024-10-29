package com.gestion.empresa.backend.gestion_empresa.servicesImpl;


import com.gestion.empresa.backend.gestion_empresa.models.Empresa;
import com.gestion.empresa.backend.gestion_empresa.repositories.EmpresaRepository;
import com.gestion.empresa.backend.gestion_empresa.services.EmpresaService;
import com.gestion.empresa.backend.gestion_empresa.utils.ResponseBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

/**
 * Author: gordillox
 * Created on: 5/10/24
 */
@Service
public class EmpresaServiceImpl implements EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private S3ServiceImpl s3Service;

    @Override
    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa crearEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public void actualizarEmpresa(Empresa empresa) {
        empresaRepository.findById(empresa.getId()).map(empresaActual -> {
            empresaActual.setNombre(empresa.getNombre());
            empresaActual.setDescripcion(empresa.getDescripcion());
            empresaActual.setEmail(empresa.getEmail());
            empresaActual.setTelefono(empresa.getTelefono());
            empresaActual.setDireccion(empresa.getDireccion());
            empresaActual.setLogo(empresa.getLogo());
            empresaActual.setTipoAsignacionCita(empresa.getTipoAsignacionCita());
            empresaActual.setTipoServicio(empresa.getTipoServicio());

            return empresaRepository.save(empresaActual);
        });
    }

    @Override
    public ResponseBackend obtenerLogoBase64(Long id) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(id);

        //verificar si la empresa existe y tiene un logo
        if (!empresaOpt.isPresent() || empresaOpt.get().getLogo() == null) {
                return new ResponseBackend(false, HttpStatus.NOT_FOUND,
                "La empresa no existe");
        }

        //obtener la empresa y su URL del logo
        Empresa empresa = empresaOpt.get();
        String urlLogo = s3Service.createPresignedGetUrl(empresa.getLogo());

        //crear directorio temporal
        Path tempDir = Paths.get("temp");
        if (!Files.exists(tempDir)) {
            try {
                Files.createDirectory(tempDir);
            } catch (IOException e) {
                return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error al crear directorio temporal");
            }
        }

        //ruta del archivo
        String fileName = empresa.getNombre() + empresa.getId() + getFileExtension(empresa.getLogo());
        Path filePath = tempDir.resolve(fileName);

        //descargar el logo
        try {
            downloadImage(urlLogo, filePath);

            //leer la imagen y convertir a Base64
            byte[] imageBytes = Files.readAllBytes(filePath);
            String base64 = Base64.getEncoder().encodeToString(imageBytes);

            //extraer la extensión del archivo
            String extension = getFileExtension(filePath.toString());
            //extraer el formato
            String formato = extension.substring(1).toUpperCase();

            return new ResponseBackend(true, HttpStatus.OK, new LogoResponse(base64, "data:image/" + extension + ";base64," + base64, "image/" + extension, extension, formato));
        } catch (IOException e) {
            return new ResponseBackend(false, HttpStatus.INTERNAL_SERVER_ERROR, "Error al descargar o procesar la imagen");
        }
    }

    private void downloadImage(String urlString, Path filePath) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream in = connection.getInputStream();
             FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    private String getFileExtension(String url) {
        if (url != null && url.contains(".")) {
            return url.substring(url.lastIndexOf('.'));
        }
        return ".png";
    }

    public static class LogoResponse implements Serializable {
        private String base64Simple;
        private String base64;
        private String tipo;
        private String extension;
        private String formato;

        public LogoResponse(String base64Simple, String base64, String tipo, String extension, String formato) {
            this.base64Simple = base64Simple;
            this.base64 = base64;
            this.tipo = tipo;
            this.extension = extension;
            this.formato = formato;
        }

        public String getBase64Simple() {
            return base64Simple;
        }

        public void setBase64Simple(String base64Simple) {
            this.base64Simple = base64Simple;
        }

        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public void setFormato(String formato) {
            this.formato = formato;
        }

        public String getFormato() {
            return formato;
        }
    }

}
