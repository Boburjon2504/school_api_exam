package com.example.maktabapi_exam.exeption;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
@EqualsAndHashCode(callSuper = false)
public class Not_Found extends RuntimeException{

    public Not_Found( String xatolik) {
        System.out.println("Bunday id li class mavjud emas");
    }
}
