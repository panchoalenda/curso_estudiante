package es.falenda.app.services;

import es.falenda.app.models.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public Image save(MultipartFile archive);

}
