package com.example.spring_boot_booking_hotel.fileUpload;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new IOException("Could not create directories: " + uploadDir, e);
            }
        }
        try {
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }
}
