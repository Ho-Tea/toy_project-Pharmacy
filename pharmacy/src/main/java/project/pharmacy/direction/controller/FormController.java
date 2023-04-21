package project.pharmacy.direction.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import project.pharmacy.direction.dto.InputDto;
import project.pharmacy.pharmacy.service.PharmacyRecommendationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class FormController {
    private final PharmacyRecommendationService pharmacyRecommendationService;


    @GetMapping
    public String main(){
        return "main";
    }

    @PostMapping("search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto)  {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress()));

        return modelAndView;
    }
}
