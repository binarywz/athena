package binary.wz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binarywz
 * @date 2022/2/12 11:29
 * @description:
 */
@RestController
public class KitController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello docker.";
    }
}
