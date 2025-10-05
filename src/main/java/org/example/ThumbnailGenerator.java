package com.mycompany.app;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import org.example.ImageProcessor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


//AWS Lambda handler that triggers on S3 object creation events.
public class ThumbnailGenerator implements RequestHandler<S3Event, String> {

    private static final String DESTINATION_BUCKET = "your-thumbnails-panda";
    private final S3Client s3Client = S3Client.builder().build();
    private final ImageProcessor imageProcessor = new ImageProcessor();

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        try {
            for (S3EventNotificationRecord record : s3event.getRecords()) {
                String sourceBucket = record.getS3().getBucket().getName();
                String objectKey = URLDecoder.decode(record.getS3().getObject().getKey(), StandardCharsets.UTF_8);

                context.getLogger().log("Processing file: " + objectKey + " from bucket: " + sourceBucket);

                // download the image from S3
                InputStream imageInputStream = s3Client.getObject(GetObjectRequest.builder()
                        .bucket(sourceBucket)
                        .key(objectKey)
                        .build());

                ByteArrayOutputStream thumbnailOutputStream = imageProcessor.createThumbnail(imageInputStream);

                s3Client.putObject(PutObjectRequest.builder()
                        .bucket(DESTINATION_BUCKET)
                        .key(objectKey)
                        .contentType("image/jpeg")
                        .build(), RequestBody.fromInputStream(new ByteArrayInputStream(thumbnailOutputStream.toByteArray()), thumbnailOutputStream.size()));

                context.getLogger().log("Successfully created thumbnail for " + objectKey);
            }
            return "Ok";
        } catch (Exception e) {
            context.getLogger().log("Error processing S3 event: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}