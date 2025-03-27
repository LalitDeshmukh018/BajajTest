import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        try {
            String studentJson = "{\n" +
                    "  \"institute_name\": \"xyz\",\n" +
                    "  \"student\": {\n" +
                    "    \"first_name\": \"virat\",\n" +
                    "    \"roll_number\": \"545\"\n" +
                    "  }\n" +
                    "}";

            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonRoot = jsonMapper.readTree(studentJson);

            String firstName = jsonRoot.path("student").path("first_name").asText().trim().toLowerCase();
            String rollNumber = jsonRoot.path("student").path("roll_number").asText().trim().toLowerCase();

            String hashInput = firstName + rollNumber;
            String uniqueHash = computeMD5(hashInput);

            saveToFile("output.txt", uniqueHash);

            System.out.println("MD5 Hash stored in output.txt: " + uniqueHash);
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
            error.printStackTrace();
        }
    }

    private static String computeMD5(String text) {
        try {
            MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
            byte[] digest = mdAlgorithm.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexOutput = new StringBuilder();
            for (byte b : digest) {
                hexOutput.append(String.format("%02x", b));
            }
            return hexOutput.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error computing MD5 hash", e);
        }
    }

    private static void saveToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (Exception e) {
            System.err.println("Failed to save output: " + e.getMessage());
        }
    }
}
