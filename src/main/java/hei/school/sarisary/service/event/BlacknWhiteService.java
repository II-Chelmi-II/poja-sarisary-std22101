package hei.school.sarisary.service.event;

import org.springframework.stereotype.Service;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

@Service
public class BlacknWhiteService {

    public void processImage(String id, byte[] imageData) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            BufferedImage originalImage = ImageIO.read(inputStream);
            BufferedImage blackAndWhiteImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics graphics = blackAndWhiteImage.getGraphics();
            graphics.drawImage(originalImage, 0, 0, null);
            graphics.dispose();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(blackAndWhiteImage, "png", outputStream);
            byte[] blackAndWhiteImageData = outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOriginalUrlById(String id) {
        return "https://original.url/" + id;
    }

    public String getTransformedUrlById(String id) {
        return "https://transformed.url/" + id;
    }
}
