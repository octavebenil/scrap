package com.sbosoft.scrap.utils;

import org.jsoup.Jsoup;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class DownloadImage {
    public static String storeImageIntoFS(String imageUrl, String fileName, String rootTargetDirectory) {
        String imagePath = null;
        try {
            byte[] bytes = Jsoup.connect(imageUrl).ignoreContentType(true).validateTLSCertificates(false).execute().bodyAsBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            imagePath = rootTargetDirectory + "/"+fileName;
            saveByteBufferImage(buffer, rootTargetDirectory, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public static void saveByteBufferImage(ByteBuffer imageDataBytes, String rootTargetDirectory, String savedFileName) {
        String uploadInputFile = rootTargetDirectory + "/" + savedFileName;

        File rootTargetDir = new File(rootTargetDirectory);
        if (!rootTargetDir.exists()) {
            boolean created = rootTargetDir.mkdirs();
            if (!created) {
                System.out.println("Error while creating directory for location- " + rootTargetDirectory);
            }
        }
        String[] fileNameParts = savedFileName.split("\\.");
        String format = fileNameParts[fileNameParts.length - 1];

        File file = new File(uploadInputFile);
        BufferedImage bufferedImage;

        InputStream in = new ByteArrayInputStream(imageDataBytes.array());
        try {
            bufferedImage = ImageIO.read(in);
            ImageIO.write(bufferedImage, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
