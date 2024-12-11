package com.eventhub.dti.infrastructure.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eventhub.dti.common.exceptions.InvalidImageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(String base64Image) {
        try {
            if (base64Image == null || base64Image.isEmpty()) {
                throw new InvalidImageException("Image cannot be empty");
            }

            String imageData = base64Image;
            if (base64Image.contains(",")) {
                String[] parts = base64Image.split(",");
                if (parts.length != 2) {
                    throw new InvalidImageException("Invalid base64 image format");
                }

                String mimeType = parts[0].toLowerCase();
                if (!mimeType.contains("image/")) {
                    throw new InvalidImageException("Invalid image type. Only images are allowed");
                }

                imageData = parts[1];
            }

            byte[] decodedBytes;
            try {
                decodedBytes = Base64.getDecoder().decode(imageData);

                if (decodedBytes.length > 10 * 1024 * 1024) {
                    throw new InvalidImageException("Image size too large. Maximum size is 10MB");
                }
            } catch (IllegalArgumentException e) {
                throw new InvalidImageException("Invalid base64 image format");
            }

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    decodedBytes,
                    ObjectUtils.asMap(
                            "public_id", "event-" + UUID.randomUUID().toString(),
                            "folder", "eventhub/events",
                            "resource_type", "image",
                            "allowed_formats", new String[] { "jpg", "jpeg", "png", "gif" },
                            "format", "jpg"));

            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            log.error("Error uploading image to Cloudinary", e);
            if (e.getMessage().contains("Invalid image file")) {
                throw new InvalidImageException("Invalid image format. Please use JPG, JPEG, PNG, or GIF format");
            }
            throw new InvalidImageException("Failed to upload image: " + e.getMessage(), e);
        }
    }
}