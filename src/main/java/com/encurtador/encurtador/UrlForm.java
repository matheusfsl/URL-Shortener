package com.encurtador.encurtador;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlForm {

    @NotNull(message = "The original URL cannot be null.")
    @NotEmpty(message = "The original URL cannot be empty.")
    @Size(max = 2048, message = "The original URL cannot exceed 2048 characters.")
    @URL(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    private String longUrl;

}
