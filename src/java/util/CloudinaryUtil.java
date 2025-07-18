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
        // Try multiple resource paths
        String[] possiblePaths = {
            "secrets.properties",
            "/secrets.properties",
            "WEB-INF/secrets.properties",
            "util/secrets.properties"
        };
        InputStream input = null;
        for (String path : possiblePaths) {
            try {
                input = CloudinaryUtil.class.getClassLoader().getResourceAsStream(path);
                if (input != null) {
                    secretProps.load(input);
                    System.out.println("CloudinaryUtil: Loaded secrets.properties from path: " + path);
                    break;
                }
            } catch (IOException ex) {
                System.err.println("CloudinaryUtil: Failed to load secrets.properties from path: " + path);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        System.err.println("CloudinaryUtil: Error closing input stream for path: " + path);
                    }
                }
            }
        }

        if (input == null) {
            System.err.println("!!! FATAL ERROR: secrets.properties NOT FOUND ON CLASSPATH !!!");
            throw new IllegalStateException("secrets.properties file not found in classpath");
        }
    }

    public static Cloudinary getInstance() {
        if (cloudinaryInstance == null) {
            String cloudName = secretProps.getProperty("cloudinary.cloud_name");
            String apiKey = secretProps.getProperty("cloudinary.api_key");
            String apiSecret = secretProps.getProperty("cloudinary.api_secret");

            if (cloudName == null || cloudName.trim().isEmpty() || 
                apiKey == null || apiKey.trim().isEmpty() || 
                apiSecret == null || apiSecret.trim().isEmpty()) {
                System.err.println("CloudinaryUtil: Missing or empty credentials - cloud_name: " + cloudName + 
                                 ", api_key: " + apiKey + ", api_secret: [hidden]");
                throw new IllegalStateException("Cloudinary credentials are not configured in secrets.properties.");
            }

            Map<String, Object> config = new HashMap<>();
            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);
            config.put("secure", true);

            System.out.println("CloudinaryUtil: Initializing Cloudinary with cloud_name: " + cloudName);
            synchronized (CloudinaryUtil.class) {
                if (cloudinaryInstance == null) {
                    cloudinaryInstance = new Cloudinary(config);
                }
            }
        }
        return cloudinaryInstance;
    }

    public static Map<String, String> uploadImageBytes(byte[] fileBytes, String folder, String publicIdPrefix) throws IOException {
        if (fileBytes == null || fileBytes.length == 0) {
            throw new IOException("File bytes are empty or null.");
        }

        Cloudinary cloudinary = getInstance();
        
        Map<String, Object> params = new HashMap<>();
        params.put("folder", folder);
        params.put("resource_type", "auto");

        System.out.println("CloudinaryUtil: Uploading image to folder: " + folder);
        Map uploadResult = cloudinary.uploader().upload(fileBytes, params);
        
        Map<String, String> result = new HashMap<>();
        result.put("secure_url", (String) uploadResult.get("secure_url"));
        result.put("public_id", (String) uploadResult.get("public_id"));
        System.out.println("CloudinaryUtil: Image uploaded - secure_url: " + result.get("secure_url") + 
                          ", public_id: " + result.get("public_id"));
        return result;
    }

    public static boolean deleteImage(String publicId) {
        if (publicId == null || publicId.trim().isEmpty()) {
            System.out.println("CloudinaryUtil: No public_id provided for deletion");
            return true;
        }
        try {
            Cloudinary cloudinary = getInstance();
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            String deletionResult = (String) result.get("result");
            
            System.out.println("CloudinaryUtil: Deletion result for '" + publicId + "': " + deletionResult);
            return "ok".equals(deletionResult) || "not found".equals(deletionResult);
        } catch (IOException e) {
            System.err.println("CloudinaryUtil: Error deleting image with public_id '" + publicId + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}