package com.example.maktabapi_exam.controller;

import com.example.maktabapi_exam.dto.ApiResponse;
import com.example.maktabapi_exam.dto.ClassDto;
import com.example.maktabapi_exam.entity.Class;
import com.example.maktabapi_exam.exeption.Not_Found;
import com.example.maktabapi_exam.repository.ClassRepository;
import com.example.maktabapi_exam.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    ClassRepository classRepository;


    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Class> all = classRepository.findAll();
        if (all.isEmpty()) {
            return new HttpEntity<Not_Found>(new Not_Found("List bo'sh"));
        }
        return ResponseEntity.ok().body(all);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getByID(@PathVariable Integer id) {
        Optional<Class> classOptional = classRepository.findById(id);
        if (classOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(classOptional.get());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public HttpEntity<?> add( @RequestBody ClassDto classDto) {
        ApiResponse add = classService.add(classDto);
        return ResponseEntity.status(add.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(add);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,  @RequestBody ClassDto classDto) {
        ApiResponse edit = classService.edit(id, classDto);
        return ResponseEntity.status(edit.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(edit);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delet(@PathVariable Integer id) {
        ApiResponse delet = classService.delet(id);
        return ResponseEntity.status(delet.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(delet);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/bos")
    public HttpEntity<?> getOneWithBook(@PathVariable Integer id){
       ApiResponse getOneWithBook = classService.getOneWithBook(id);
       return ResponseEntity.ok().body(getOneWithBook);
    }
}
