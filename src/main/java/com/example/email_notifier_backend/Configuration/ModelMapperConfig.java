package com.example.email_notifier_backend.Configuration;
////
////
////import org.modelmapper.ModelMapper;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////
////@Configuration
////public class ModelMapperConfig {
////
////    @Bean
////    public ModelMapper modelMapper() {
////        return new ModelMapper();
////    }
////}
//
//
import com.example.email_notifier_backend.Dto.EventResponseDTO;
import com.example.email_notifier_backend.Dto.NotificationDTO;
import com.example.email_notifier_backend.Entity.Events;
import com.example.email_notifier_backend.Entity.NotificationLog;
import com.example.email_notifier_backend.Entity.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {



    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();


        AbstractConverter<User, Long> userToIdConverter = new AbstractConverter<User, Long>() {
            @Override
            protected Long convert(User user) {
                try {
                    return user != null ? user.getId() : null;
                } catch (Exception e) {
                    return null;
                }
            }
        };

        mapper.addConverter(userToIdConverter);

        mapper.typeMap(Events.class, EventResponseDTO.class).addMappings(map -> {
            map.map(Events::getId, EventResponseDTO::setId);
            map.map(Events::getTitle, EventResponseDTO::setTitle);
            map.map(Events::getDate, EventResponseDTO::setDate);
            map.map(Events::getTime, EventResponseDTO::setTime);
            map.map(Events::getDescription, EventResponseDTO::setDescription);
            map.map(Events::isApproved, EventResponseDTO::setApproved);
            map.using(userToIdConverter).map(Events::getUser, EventResponseDTO::setUserId);
        });

        // NotificationLog -> NotificationDTO mapping
        mapper.typeMap(NotificationLog.class, NotificationDTO.class).addMappings(map -> {
            map.map(NotificationLog::getId, NotificationDTO::setId);
            map.map(NotificationLog::getStatus, NotificationDTO::setStatus);
            map.map(NotificationLog::getSentTimestamp, NotificationDTO::setSentAt);
            map.<String>map(
                    src -> src.getEvent() != null && src.getEvent().getUser() != null ?
                            src.getEvent().getUser().getEmail() : null,
                    NotificationDTO::setRecipientEmail);
            map.<String>map(
                    src -> src.getEvent() != null ? src.getEvent().getTitle() : null,
                    NotificationDTO::setSubject);
        });

        return mapper;
    }

}
