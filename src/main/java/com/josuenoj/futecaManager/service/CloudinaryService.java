package com.josuenoj.futecaManager.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String, Object> uploadProfilePicture(MultipartFile file, String folder) throws IOException {
        String originalFilename = file.getOriginalFilename();

        if(originalFilename == null || originalFilename.isEmpty()){
            throw new IllegalArgumentException("El archivo no puede ser nulo o estar vacío");
        } else {
            //Desfragmentar el nombre y extensión para crear un archivo con nombre nuevo
            String newName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String finalName = newName + "-" + timestamp;


            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", folder,
                "public_id", finalName
            ));

            return uploadResult;
        }
    }
}
