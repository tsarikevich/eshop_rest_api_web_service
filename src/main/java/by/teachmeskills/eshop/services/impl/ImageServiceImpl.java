package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.repositories.ImageRepository;
import by.teachmeskills.eshop.services.ImageService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image create(Image entity) {
        return imageRepository.save(entity);
    }

    @Override
    public List<Image> read() {
        return imageRepository.findAll();
    }

    @Override
    public Image update(Image entity) {
        Optional<Image> image = imageRepository.findById(entity.getId());
        if (image.isPresent()) {
            return imageRepository.save(entity);
        }
        log.error("Image doesn't exist");
        return null;
    }

    @Override
    public void delete(int id) {
        imageRepository.deleteById(id);
    }
}
