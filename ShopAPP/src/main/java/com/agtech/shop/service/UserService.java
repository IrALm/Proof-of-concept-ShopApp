package com.agtech.shop.service;

import com.agtech.shop.entity.User;
import com.agtech.shop.mapper.UserMapper;
import com.agtech.shop.model.dto.UserDTO;
import com.agtech.shop.model.formDto.UserFormDTO;
import com.agtech.shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PdfService pdfService;
    private final MinioService minioService;
    private final MailService mailService;

    @Transactional
    public UserDTO createUser(UserFormDTO userFormDTO){

        if( userRepository.existsByEmail(userFormDTO.email())){
            throw new IllegalArgumentException("l'Email déjà utilisé");
        }
        User user = new User();
        user.setFirstName(userFormDTO.firstName());
        user.setLastName(userFormDTO.lastName());
        user.setEmail(userFormDTO.email());
        user.setOrders(new ArrayList<>());
        userRepository.save(user);

        return UserMapper.INSTANCE.toDTO(user);
    }

    public void generateAndSendInvoice(UserDTO user){

        Map<String , Object> data = buildInvoiceData(user);
        byte[] pdf = pdfService.generatePdf(data);
        String filename = minioService.uploadPdf(pdf , "invoice.pdf");
        mailService.sendInvoiceMail(user.getEmail() , pdf);
    }

    private Map<String , Object> buildInvoiceData(UserDTO user){
        return Map.of(
                "prenomClient" , user.getFirstName(),
                "nomClient" , user.getLastName(),
                "date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM:yyyy")),
                "heure", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                "description" , "Abonnement Premium"

        );
    }

}
