package com.example.maktabapi_exam.repository;

import com.example.maktabapi_exam.entity.Attachmentt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachmentt,Integer> {
}
