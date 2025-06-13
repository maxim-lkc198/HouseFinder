/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CloudinaryUtil {

    private static Cloudinary cloudinaryInstance = null;
    private static Properties secretProps = new Properties();

    static {
        try (InputStream input = CloudinaryUtil.class.getClassLoader().getResourceAsStream("secrets.properties")) {
            if (input == null) {
                System.err.println("!!! FATAL ERROR: secrets.properties NOT FOUND ON CLASSPATH !!!");
            } else {
                secretProps.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Cloudinary getInstance() {
        if (cloudinaryInstance == null) {
            String cloudName = secretProps.getProperty("cloudinary.cloud_name");
            String apiKey = secretProps.getProperty("cloudinary.api_key");
            String apiSecret = secretProps.getProperty("cloudinary.api_secret");

            if (cloudName == null || apiKey == null || apiSecret == null) {
                throw new IllegalStateException("Cloudinary credentials are not configured in secrets.properties.");
            }

            Map<String, Object> config = new HashMap<>();
            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);
            config.put("secure", true); 

            synchronized (CloudinaryUtil.class) {
                if (cloudinaryInstance == null) {
                    cloudinaryInstance = new Cloudinary(config);
                }
            }
        }
        return cloudinaryInstance;
    }

    /**
     * Uploads an image file from a byte array to a specified folder on Cloudinary.
     * @param fileBytes The byte array of the file.
     * @param folder The target folder on Cloudinary (e.g., "housefinder/posts").
     * @param publicIdPrefix Optional prefix for the public_id. A random string will be appended.
     * @return A Map containing the "secure_url" and "public_id" of the uploaded image.
     * @throws IOException If the file bytes are empty or an upload error occurs.
     */
    public static Map<String, String> uploadImageBytes(byte[] fileBytes, String folder, String publicIdPrefix) throws IOException {
        if (fileBytes == null || fileBytes.length == 0) {
            throw new IOException("File bytes are empty or null.");
        }

        Cloudinary cloudinary = getInstance();
        
        Map<String, Object> params = new HashMap<>();
        params.put("folder", folder);
        // Let Cloudinary generate a random public_id to avoid overwrites
        // If you want to control the name, you can add 'public_id' parameter
        // params.put("public_id", publicIdPrefix + "_" + System.currentTimeMillis());
        params.put("resource_type", "auto");

        Map uploadResult = cloudinary.uploader().upload(fileBytes, params);
        
        Map<String, String> result = new HashMap<>();
        result.put("secure_url", (String) uploadResult.get("secure_url"));
        result.put("public_id", (String) uploadResult.get("public_id"));
        return result;
    }
    
    

    /**
     * Deletes an image from Cloudinary using its public ID.
     * @param publicId The full public ID of the image to delete (including folder path).
     * @return true if deletion was successful or the image didn't exist.
     */
    public static boolean deleteImage(String publicId) {
        if (publicId == null || publicId.isEmpty()) {
            return true; // Nothing to delete
        }
        try {
            Cloudinary cloudinary = getInstance();
            // The destroy method deletes the resource.
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String deletionResult = (String) result.get("result");
            
            System.out.println("CloudinaryUtil: Deletion result for '" + publicId + "': " + deletionResult);
            // "ok" means successfully deleted, "not found" is also a success from our perspective.
            return "ok".equals(deletionResult) || "not found".equals(deletionResult);
        } catch (IOException e) {
            System.err.println("CloudinaryUtil: Error deleting image with public_id '" + publicId + "'.");
            e.printStackTrace();
            return false;
        }
    }
}