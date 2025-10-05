package org.example;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String INPUT_DIR = "imagesToConvert";
    private static final String OUTPUT_DIR = "convertedImages";

    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();
        File inputDir = new File(INPUT_DIR);
        File outputDir = new File(OUTPUT_DIR);

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            System.err.println("Error: Input directory '" + INPUT_DIR + "' not found.");
            System.err.println("Please create it in the same folder as the JAR file and place images inside.");
            return;
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        System.out.println("Scanning for images in ./" + INPUT_DIR);

        File[] files = inputDir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No images found to convert.");
            return;
        }

        for (File sourceFile : files) {
            if (sourceFile.isFile()) {
                Path outputPath = Paths.get(OUTPUT_DIR, sourceFile.getName());
                System.out.println("Processing: " + sourceFile.getName());

                try (InputStream inputStream = new FileInputStream(sourceFile);
                     OutputStream outputStream = new FileOutputStream(outputPath.toFile())) {

                    ByteArrayOutputStream thumbnailData = imageProcessor.createThumbnail(inputStream);
                    outputStream.write(thumbnailData.toByteArray());

                    System.out.println(" -> Saved to: " + outputPath);

                } catch (IOException e) {
                    System.err.println("Failed to process " + sourceFile.getName() + ": " + e.getMessage());
                }
            }
        }
        System.out.println("\nBatch processing complete.");
    }
}
