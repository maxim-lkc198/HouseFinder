/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.Part;
import model.Image;
import service.ImageUploadService;
import util.CloudinaryUtil;

public class ImageUploadServiceImpl implements ImageUploadService {

    @Override
    public List<Image> uploadPostImages(List<Part> imageParts, long postId, String thumbnailInputName) throws IOException {
        List<Image> uploadedImages = new ArrayList<>();
        
        for (Part part : imageParts) {
            if (part != null && part.getSize() > 0) {
                byte[] fileBytes = part.getInputStream().readAllBytes();
                String folder = "housefinder/posts"; // Thư mục trên Cloudinary

                // Upload lên Cloudinary
                Map<String, String> uploadResult = CloudinaryUtil.uploadImageBytes(fileBytes, folder, "post_" + postId);
                
                Image img = new Image();
                img.setPostId(postId);
                img.setImageUrl(uploadResult.get("secure_url"));
                img.setCloudinaryPublicId(uploadResult.get("public_id"));
                
                // Kiểm tra xem đây có phải là ảnh thumbnail được chọn không
                if (part.getName().equals(thumbnailInputName)) {
                    img.setThumbnail(true);
                } else {
                    img.setThumbnail(false);
                }
                
                uploadedImages.add(img);
            }
        }
        return uploadedImages;
    }
}
