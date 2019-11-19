package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.services.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Loggable
@RequestMapping("/sections")
public class SectionController {
    private static final Logger logger = LoggerFactory.getLogger(SectionController.class);

    @Autowired
    SectionService sectionService;

    // http://localhost:2019/sections/sections
    @GetMapping(value = "/sections", produces = {"application/json"})
    public ResponseEntity<?> listAllSections(HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Section> mySections = sectionService.findAll();
        return new ResponseEntity<>(mySections, HttpStatus.OK);
    }


    // http://localhost:2019/sections/section/{sectionId}
    @GetMapping(value = "/section/{sectionId}", produces = {"application/json"})
    public ResponseEntity<?> getSectionById(HttpServletRequest request, @PathVariable Long sectionId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        Section s = sectionService.findSectionById(sectionId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
