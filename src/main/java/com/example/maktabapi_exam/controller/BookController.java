package com.example.maktabapi_exam.controller;

import com.example.maktabapi_exam.dto.ApiResponse;
import com.example.maktabapi_exam.dto.BookDto;
import com.example.maktabapi_exam.entity.AttachmentContent;
import com.example.maktabapi_exam.entity.Attachmentt;
import com.example.maktabapi_exam.entity.Book;
import com.example.maktabapi_exam.entity.enums.BookLanguage;
import com.example.maktabapi_exam.exeption.Not_Found;
import com.example.maktabapi_exam.repository.AttachmentContentRepository;
import com.example.maktabapi_exam.repository.AttachmentRepository;
import com.example.maktabapi_exam.repository.BookRepository;
import com.example.maktabapi_exam.service.BookServie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    static BookLanguage bookLanguage;
    final BookServie bookServie;
    final BookRepository bookRepository;
final AttachmentRepository attachmentRepository;
final AttachmentContentRepository attachmentContentRepository;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public HttpEntity<?> add( @ModelAttribute  BookDto bookDto) {
        ApiResponse add = bookServie.add(bookDto);
        return ResponseEntity.ok().body(add);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/language")
    public HttpEntity<?> getBookLanguage(@PathVariable Integer id){
        Optional<Book> byId = bookRepository.findById(id);
        String language = byId.get().getLanguage();
        return ResponseEntity.ok().body("book " + byId.get().getName()+" language "+ language);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public HttpEntity<?> getAll(){
        List<Book> all = bookRepository.findAll();
        if (all.isEmpty()) {
            return new HttpEntity<Not_Found>(new Not_Found("List bo'sh"));
        }
        return ResponseEntity.ok().body(all);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOneId(@PathVariable Integer id){
        ApiResponse apiResponse=bookServie.getOneId(id);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.NO_CONTENT).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id ,  @RequestBody BookDto bookDto){
        ApiResponse edit=bookServie.edit(id , bookDto);
        return ResponseEntity.status(edit.isSuccess()?HttpStatus.OK:HttpStatus.NO_CONTENT).body(edit);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delet(@PathVariable Integer id){
        ApiResponse delet=bookServie.delet(id);
        return ResponseEntity.status(delet.isSuccess()?HttpStatus.OK:HttpStatus.NO_CONTENT).body(delet);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/language")
    public HttpEntity<?> getlanguages(){
        List<String> roles = new ArrayList<>();
       for (BookLanguage bookLanguage:BookLanguage.values()){
           roles.add(String.valueOf(bookLanguage));
       }
        return ResponseEntity.ok().body(roles);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/download/{id}")
    public void getFromDb(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachmentt> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachmentt attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(id);
            AttachmentContent attachmentContent = optionalAttachmentContent.get();
            if (optionalAttachmentContent.isPresent()) {

                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", attachment.getName() + "/:" + attachment.getSize());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
    }
}
