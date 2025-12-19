package com.agtech.shop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendInvoiceMail(String to, byte[] pdf) {
        try {
            // 1. Crée un MimeMessage vide qui représentera l'email à envoyer
            MimeMessage message = mailSender.createMimeMessage();

            // 2. Crée un helper pour configurer facilement le message
            //    - définit le destinataire, l'expéditeur, le sujet et le contenu HTML
            MimeMessageHelper helper = getMimeMessageHelper(to, message);

            // 3. Ajoute une image inline dans l'email
            //    - "logo" est l'ID utilisé dans le HTML <img src='cid:logo'/>
            //    - ClassPathResource pointe vers le fichier logo.png dans /static/pdf/
            helper.addInline("logo", new ClassPathResource("static/pdf/logo.png"));

            // 4. Ajoute le PDF généré comme pièce jointe
            //    - "document.pdf" est le nom visible de la pièce jointe
            //    - ByteArrayResource contient le PDF en mémoire
            helper.addAttachment("document.pdf", new ByteArrayResource(pdf));

            // 5. Envoie l'email via le mailSender configuré
            mailSender.send(message);

        } catch (Exception e) {
            // 6. En cas d'erreur (messagerie, fichier introuvable…), affiche la stack trace
            e.printStackTrace();
        }
    }


    @NotNull
    private static MimeMessageHelper getMimeMessageHelper(String to, MimeMessage message) throws MessagingException {

        // 1. Crée un helper pour le message MIME
        //    - message : le MimeMessage à configurer
        //    - true : indique que le mail peut contenir du HTML et des pièces jointes
        //    - "UTF-8" : encodage du contenu
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 2. Définit le destinataire de l'email
        helper.setTo(to);

        // 3. Définit l'expéditeur de l'email
        helper.setFrom("noreplydevback@gmail.com");

        // 4. Définit l'objet (le sujet) de l'email
        helper.setSubject("Votre Facture");

        // 5. Définit le contenu HTML de l'email
        //    - true indique que le contenu est du HTML (sinon ce serait du texte brut)
        //    - le HTML contient un <img src='cid:logo'/> pour l'image inline
        helper.setText("""
            <html>
                <body>
                    <img src= 'cid:logo'/>
                    <p>Bonjour,</p>
                    <p>Veuillez trouver votre document en pièce jointe.</p>
                </body>
            </html>
            """, true);

        // 6. Retourne le helper configuré
        return helper;
    }

}
