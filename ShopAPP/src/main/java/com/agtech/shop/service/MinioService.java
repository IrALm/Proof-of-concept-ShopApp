package com.agtech.shop.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Service
public class MinioService {

    private final MinioClient minioClient;

    private final String bucketProduct;

    private final String bucketInvoice;

    public MinioService(MinioClient minioClient,
                        @Value("${minio.bucket.products}") String bucketProduct,
                        @Value("${minio.bucket.invoices}") String bucketInvoice) {
        this.minioClient = minioClient;
        this.bucketProduct = bucketProduct;
        this.bucketInvoice = bucketInvoice;
    }

    public String uploadImage( MultipartFile file){

        try{

            // Génère un nom unique pour éviter les collisions des fichiers
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // Envoie du fichier vers MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketProduct)
                            .object(fileName)
                            .stream(
                                    file.getInputStream(), // flux du fichier
                                    file.getSize(), // Taille connue
                                    -1 // taille inconnue
                            )
                            .contentType(file.getContentType())// Type MIME (image/png, pdf, etc.)
                            .build()
            );
            // construction de l'Url publique permanente
            return "http://127.0.0.1:9000/" + bucketProduct + "/" + fileName;
        } catch (Exception e){
             throw new RuntimeException("Erreur lors de l'upload vers MinIO" , e);
        }
    }

    public String uploadPdf(byte[] pdf, String originalName) {

        try {
            // 1. Génère un nom de fichier unique pour éviter les collisions
            //    - UUID.randomUUID() : identifiant unique
            //    - "_" + originalName : conserve le nom original pour référence
            String filename = UUID.randomUUID() + "_" + originalName;

            // 2. Envoie le PDF sur le serveur MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketInvoice)          // Nom du bucket où le fichier sera stocké
                            .object(filename)               // Nom du fichier dans le bucket
                            .stream(
                                    new ByteArrayInputStream(pdf), // Convertit le tableau d'octets en InputStream
                                    pdf.length,                     // Taille du PDF
                                    -1                               // Taille inconnue du stream (-1 si connue)
                            )
                            .contentType("application/pdf")  // Définit le type MIME du fichier
                            .build()
            );

            // 3. Retourne le nom du fichier stocké (utile pour sauvegarde en base ou référence)
            return filename;

        } catch (Exception e) {
            // 4. Si une erreur survient lors de l'upload, on lève une RuntimeException
            throw new RuntimeException("Erreur upload PDF MinIO", e);
        }
    }
}
