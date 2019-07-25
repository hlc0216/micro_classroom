package cc.mrbird.web.controller.zdu;

import cc.mrbird.web.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/corrWord")
public class CorrWordController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/index")
    public String index() {
        return "system/corrWord/corrWord";
    }

    @RequestMapping("/student")
    public ModelAndView student(String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("system/corrWord/student");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

}
