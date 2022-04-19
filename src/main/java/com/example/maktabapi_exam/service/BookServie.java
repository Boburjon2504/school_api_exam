package com.example.maktabapi_exam.service;

import com.example.maktabapi_exam.dto.ApiResponse;
import com.example.maktabapi_exam.dto.BookDto;
import com.example.maktabapi_exam.entity.Book;
import com.example.maktabapi_exam.entity.Class;
import com.example.maktabapi_exam.repository.BookRepository;
import com.example.maktabapi_exam.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServie {

    final BookRepository bookRepository;
    final ClassRepository classRepository;

    @SneakyThrows
    public ApiResponse add(@RequestBody BookDto bookDto){
        Optional<Class> byId = classRepository.findById(bookDto.getClassId());
        if (!byId.isPresent()){
            return new ApiResponse("not found",false);
        }
        String fileName = bookDto.getFile().getOriginalFilename();
        Book book = new Book(fileName, bookDto.getFile().getSize(), bookDto.getFile().getContentType());

        book.setAClass(byId.get());
        book.setLanguage(bookDto.getLanguage());
        book.setName(bookDto.getName());
        Book save = bookRepository.save(book);

        return new ApiResponse("added",true,save);
    }

    public ApiResponse edit(Integer id, BookDto bookDto) {
        Optional<Book> byId = bookRepository.findById(id);
        Optional<Class> byId1 = classRepository.findById(bookDto.getClassId());

        if (!byId.isPresent()) {
            return new ApiResponse("Bunday id li book mavjud emas", false);
        }
        Book book = byId.get();
        book.setName(bookDto.getName());
        book.setLanguage(bookDto.getLanguage());
        book.setAClass(byId1.get());
        Book save = bookRepository.save(book);
        return new ApiResponse("Mana",true,save);
    }


    public ApiResponse delet(Integer id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Bunday id li book mavjud emas", false);
        }
        bookRepository.deleteById(id);
        return new ApiResponse("O'chirildi", true);
    }
    public ApiResponse getOneId(Integer id) {

        Optional<Book> byId = bookRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Bunday id li book mavjud emas", false);
        }
        return new ApiResponse("Mana", true, byId.get());
    }
}
