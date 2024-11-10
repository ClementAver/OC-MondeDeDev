package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedRequest {
    @NotNull(message = "Le nombre d'articles désiré est obligatoire.")
    @Min(value = 1, message = "Le nombre d'articles désiré doit être supérieur à un (1).")
    int limit;

    @NotNull(message = "L'index de départ est obligatoire.")
    @Min(value = 0, message = "L'index de départ doit être égal ou supérieur à un (0).")
    int offset;
}
