package es.falenda.app.services;

import es.falenda.app.models.Image;
import es.falenda.app.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    ImageRepository imageRepository;

    @Override
    @Transactional
    public Image save(MultipartFile archive) {
            if (!archive.isEmpty()) {
                try {
                    Image image = new Image();
                    image.setMime(archive.getContentType());
                    image.setName(archive.getName());
                    image.setContent(archive.getBytes());
                    return this.imageRepository.save(image);
                } catch (Exception e) {
                  System.err.println(e.getMessage());
                }
            }
            return null;
        }
    }

