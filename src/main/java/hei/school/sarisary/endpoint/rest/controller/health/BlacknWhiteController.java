package hei.school.sarisary.endpoint.rest.controller.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hei.school.sarisary.service.event.BlacknWhiteService;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

@RestController
public class BlacknWhiteController {

    @Autowired
    private BlacknWhiteService blacknWhiteService;

    @PutMapping("/black-and-white/{id}")
    public ResponseEntity<Void> putBlackAndWhite(@PathVariable String id, @RequestBody byte[] imageData) {
        try {
            // Convertir l'image en noir et blanc
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));
            BufferedImage blackAndWhiteImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics graphics = blackAndWhiteImage.getGraphics();
            graphics.drawImage(originalImage, 0, 0, null);
            graphics.dispose();
            // Convertir l'image en tableau de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(blackAndWhiteImage, "png", outputStream);
            byte[] blackAndWhiteImageData = outputStream.toByteArray();
            // Appeler le service pour enregistrer l'image en noir et blanc
            blacknWhiteService.processImage(id, blackAndWhiteImageData);
            // Retourner une réponse HTTP 200 OK
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/black-and-white/{id}")
    public ResponseEntity<Map<String, String>> getBlackAndWhiteResult(@PathVariable String id) {
        String originalUrl = blacknWhiteService.getOriginalUrlById(id);
        String transformedUrl = blacknWhiteService.getTransformedUrlById(id);
        Map<String, String> result = new HashMap<>();
        result.put("originalUrl", originalUrl);
        result.put("transformedUrl", transformedUrl);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
