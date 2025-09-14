package com.example.sadad.controller;

import com.example.sadad.repository.SadadRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public/hello")
    public Map<String,String> publicHello(){
        return Map.of("message","Hello public");
    }

    @GetMapping("/entry/hello")
    @PreAuthorize("hasRole('ENTRY') or hasRole('ADMIN')")
    public Map<String,String> entryHello(){
        return Map.of("message","Hello ENTRY");
    }

    @GetMapping("/approve/hello")
    @PreAuthorize("hasRole('APPROVER') or hasRole('ADMIN')")
    public Map<String,String> approveHello(){
        return Map.of("message","Hello APPROVER");
    }

    @GetMapping("/release/hello")
    @PreAuthorize("hasRole('RELEASER') or hasRole('ADMIN')")
    public Map<String,String> releaseHello(){
        return Map.of("message","Hello RELEASER");
    }

    @Autowired
    private SadadRecordRepository recordRepository;

    @DeleteMapping("/entry/delete/{id}")
    @PreAuthorize("hasRole('ENTRY')")
    public Map<String, String> deleteRecord(@PathVariable("id") Long id) {
        recordRepository.deleteById(id); // هنا بيحذف فعليًا من DB
        if(!recordRepository.existsById(id)){
            return Map.of("message", "Record with id " + id + " not found");
        }
        recordRepository.deleteById(id);

        return Map.of("message", "Record with id " + id + " deleted successfully by ENTRY");

    }




    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String,String> adminHello(){
        return Map.of("message","Hello ADMIN");
    }
}
