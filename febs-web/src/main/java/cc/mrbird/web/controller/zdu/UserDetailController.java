package cc.mrbird.web.controller.zdu;

import cc.mrbird.gen.domain.GetUserDetails;
import cc.mrbird.security.domain.FebsUserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zch
 * @Date: 2018/12/26 14:31
 * @Description:
 */
@RestController
public class UserDetailController {
    @RequestMapping("/find")

    public Long  find(){
      FebsUserDetails userDetails =(FebsUserDetails) GetUserDetails.GetUserDetail();
      return userDetails.getDeptId();
    }
}
