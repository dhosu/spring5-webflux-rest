package guru.springframework.spring5webfluxrest.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private String id;

    private String description;
}
