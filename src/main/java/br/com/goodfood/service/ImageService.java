package br.com.goodfood.service;

import br.com.goodfood.infra.exception.FileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    // Alterar caminho em ambiente de produção
    private static final String STORAGE_PATH = System.getProperty("user.dir") + "/src/main/resources/storage/";

    public String uploadImage(MultipartFile image) throws IOException {

        String newName = this.generateNewName(image.getOriginalFilename());

        String fullFilePath = STORAGE_PATH + newName;

        image.transferTo(new File(fullFilePath));

        return newName;
    }

    public Resource getImage(String filename) {
        try {
            Path filePath = this.resolvePath(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new FileNotFoundException("Erro! Imagem não encontrada: " + filename);
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao carregar imagem: " + filename);
        }
    }

    private String generateNewName(String originalName) {
        String[] splitName = originalName.split("\\.");
        String extension = "." + splitName[1];

        return UUID.randomUUID() + extension;
    }

    public Path resolvePath(String filename) {
        return Paths.get(STORAGE_PATH).resolve(filename);
    }
}
