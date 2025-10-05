package org.example;

import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


//creates the thumbnail.
public class ImageProcessor {

    private static final int THUMBNAIL_SIZE = 200;

    public ByteArrayOutputStream createThumbnail(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(inputStream)
                .size(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
        return outputStream;
    }
}
