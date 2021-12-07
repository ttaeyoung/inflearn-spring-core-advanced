package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping
@ResponseBody
public interface OrderControllerV1 {

    String VERSION = "v1";

    @GetMapping("/" + VERSION + "/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/" + VERSION + "/no-log")
    String noLog();

}
