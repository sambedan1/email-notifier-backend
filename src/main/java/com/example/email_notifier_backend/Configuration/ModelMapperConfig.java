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

        // Events -> EventDTO mapping: set userId from event.user.id
        mapper.addConverter(new AbstractConverter<Events, EventResponseDTO>() {
            @Override
            protected EventResponseDTO convert(Events source) {
                if (source == null) return null;
                EventResponseDTO dto = new EventResponseDTO();
                dto.setTitle(source.getTitle());
                dto.setDate(source.getDate());
                dto.setTime(source.getTime());
                dto.setDescription(source.getDescription());
                dto.setApproved(source.isApproved());
                return dto;
            }
        });

        // NotificationLog -> NotificationDTO mapping
        mapper.addConverter(new AbstractConverter<NotificationLog, NotificationDTO>() {
            @Override
            protected NotificationDTO convert(NotificationLog source) {
                if (source == null) return null;
                NotificationDTO dto = new NotificationDTO();
                dto.setId(source.getId());
                dto.setStatus(source.getStatus());
                dto.setSentAt(source.getSentTimestamp());
                // derive email and subject from related event and its user
                if (source.getEvent() != null) {
                    if (source.getEvent().getUser() != null) {
                        dto.setEmail(source.getEvent().getUser().getEmail());
                    }
                    dto.setSubject(source.getEvent().getTitle());
                }
                return dto;
            }
        });

        return mapper;
    }
}
