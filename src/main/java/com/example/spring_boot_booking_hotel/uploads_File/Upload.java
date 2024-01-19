package com.example.spring_boot_booking_hotel.uploads_File;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class Upload {
    public String saveImage(MultipartFile imageFile) throws IOException {
        final String uploadDir = "./uploads"; // Đường dẫn thư mục lưu trữ // Định nghĩa đường dẫn thư mục lưu trữ ở đây
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
        Path filePath = Paths.get(uploadDir, fileName);

        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(imageFile.getBytes());
        }
        return "/img/" + fileName;
    }
}
