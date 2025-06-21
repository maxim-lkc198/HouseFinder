/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.Part;
import model.Post;
import model.User;

public interface PostService {
    /**
     * Submits a new post for approval after payment/benefit commitment.
     * @param post The Post object containing all details.
     * @param imageParts The list of uploaded image file parts.
     * @param thumbnailInputName The name of the input field chosen as thumbnail.
     * @param currentUser The currently logged-in user.
     * @return The ID of the created post.
     * @throws IOException If image upload fails.
     * @throws IllegalStateException If user exceeds post limit.
     */
    long submitNewPost(Post post, List<Part> imageParts, String thumbnailInputName, User currentUser) throws IOException, IllegalStateException;

    void payForDraftPost(long postId, User user);
}