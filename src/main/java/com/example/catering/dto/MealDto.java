package com.example.catering.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    private Long id;
    private String Name;
    private String Picture;
    private Long Cal;
}
