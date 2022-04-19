package com.example.maktabapi_exam.service;

import com.example.maktabapi_exam.dto.ApiResponse;
import com.example.maktabapi_exam.dto.ClassDto;
import com.example.maktabapi_exam.entity.Book;
import com.example.maktabapi_exam.entity.Class;
import com.example.maktabapi_exam.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassService {
    final ClassRepository classRepository;

    public ApiResponse add(ClassDto classDto) {

        Class c = new Class();
        c.setName(classDto.getName());
        c.setSinf(classDto.getSinf());
        classRepository.save(c);
        return new ApiResponse("Qo'shildi", true);
    }

    public ApiResponse edit(Integer id, ClassDto classDto) {
        Optional<Class> byId = classRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Bunday id li class mavjud emas", false);
        }
        Class c = byId.get();
        c.setSinf(classDto.getSinf());
        c.setName(classDto.getName());

        Class save = classRepository.save(c);
        return new ApiResponse("changed", true, save);
    }


    public ApiResponse delet(Integer id) {
        Optional<Class> byId = classRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Buday id li class mavjud emas", false);
        }
        classRepository.deleteById(id);
        return new ApiResponse("O'chirildi", true);
    }

    public ApiResponse getOneWithBook(Integer id) {
        Optional<Class> classOptional = classRepository.findById(id);
        if (classOptional.isEmpty()) {
            return new ApiResponse("not found", false);
        }
        List<Book> books = classOptional.get().getBooks();
        return new ApiResponse("mana", true,books);
    }
}
