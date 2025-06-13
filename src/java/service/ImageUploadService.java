/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.Part;
import model.Image;

public interface ImageUploadService {
    /**
     * Uploads multiple images for a post to Cloudinary.
     * @param imageParts The list of Part objects from the servlet request.
     * @param postId The ID of the post these images belong to.
     * @param thumbnailFieldName The 'name' attribute of the input field for the thumbnail.
     * @return A list of Image model objects ready to be saved to the database.
     * @throws IOException
     */
    List<Image> uploadPostImages(List<Part> imageParts, long postId, String thumbnailFieldName) throws IOException;
}
