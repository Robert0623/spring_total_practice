package com.myportfolio.web.controller;

import com.myportfolio.web.dao.UserDao;
import com.myportfolio.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserDao userDao;
    private final int FAIL = 0;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df2, false));
//        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor("#"));
//        binder.setValidator(new UserValidator()); //UserValidator를 WebDataBinder의 로컬 validator로 등록.
//        binder.addValidators(new UserValidator());
//        List<Validator> validatorList = binder.getValidators();
//        System.out.println("validatorList = " + validatorList);
    }
    
    @GetMapping("/add")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/add")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result = " + result);
        System.out.println("user = " + user);

//        //수동 검증 - Validator를 직접 생성하고, validate()를 직접 호출
//        UserValidator userValidator = new UserValidator();
//        userValidator.validate(user, result); //BindingResult는 Errors의 자손

        //User객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야함.
        //에러가 없으면 DB에 신규회원 정보를 저장
        if(!result.hasErrors()) {
            int rowCnt = userDao.insertUser(user);

            if(rowCnt!=FAIL)
                return "registerInfo";
        }

        //아래 유효성 검사를 UserValidator 또는 GlobalValidator로 외부에서 처리.
//        //1. 유효성 검사
//        if(!isValid(user)) {
//            //브라우저에서는 인코딩이 자동으로 변환되지만,
//            //컨트롤러에서는 URLEncoder로 인코딩 해줘야한다.
//            //또한 이를 뷰에서 URLDecoder로 디코딩 해줘야한다.
//            String msg = URLEncoder.encode("id를 잘못입력하셨습니다.", "utf-8");
//
//            //1번 방법 - URL재작성을 해서 msg를 넣기
////            return "redirect:/register/add?msg="+msg; //URL재작성(re-writing)
//
//            //2번 방법 - Model에 msg저장. 1번 방법과 같은 결과.
//            //redirect는 GET으로 보내는데, Model은 GET에서 사용 불가.
//            //따라서 스프링이 자동으로 데이터를 쿼리스트링으로 보낸다.
//            m.addAttribute("msg", msg);
////            return "redirect:/register/add";
//            return "forward:/register/add";
//        }
        return "registerForm";
    }

    private boolean isValid(User user) {
        return true;
    }

}
