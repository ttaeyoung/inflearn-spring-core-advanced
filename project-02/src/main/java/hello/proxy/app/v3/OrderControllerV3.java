
package hello.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderControllerV3 {

    private final OrderServiceV3 service;
    private final static String VERSION = "v3";

    public OrderControllerV3(OrderServiceV3 service) {
        this.service = service;
    }

    @GetMapping("/" + VERSION + "/request")
    public String request(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/" + VERSION + "/no-log")
    public String noLog() {
        return "ok";
    }
}
