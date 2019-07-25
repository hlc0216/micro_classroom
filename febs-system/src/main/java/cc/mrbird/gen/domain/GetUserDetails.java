package cc.mrbird.gen.domain;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Auther: zch
 * @Date: 2018/12/26 15:12
 * @Description:
 */
public class GetUserDetails {
    private static volatile UserDetails userDetails;

    public static UserDetails GetUserDetail() {
        if (userDetails == null) {
            synchronized (GetUserDetails.class){
                return (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
            }
        }
        return userDetails;
    }
}
