package com.bduo.newsbrief.controller;

import com.bduo.newsbrief.dto.NewsSummaryResponse;
import com.bduo.newsbrief.service.NewsBriefService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news-brief")
public class NewsBriefController {

    private final NewsBriefService newsBriefService;
    public NewsBriefController(NewsBriefService newsBriefService) {
        this.newsBriefService = newsBriefService
    }

    @GetMapping(value = "/general-brief", produces = MediaType.APPLICATION_JSON_VALUE)
    public NewsSummaryResponse generalBrief() {
        return newsBriefService.generateGeneralNewsBrief();
    }

    /*
    @GetMapping("/general-brief", produces = MediaType.APPLICATION_HTML_VALUE)
    public NewsSummaryResponse generalBrief() {
        return newsBriefService.generateGeneralNewsBrief();
    }

    */

}
