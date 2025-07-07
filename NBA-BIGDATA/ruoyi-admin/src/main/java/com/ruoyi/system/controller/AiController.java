package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.impl.DeepSeekService;
import com.ruoyi.system.service.impl.WenXinYiYanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/system/ai")
public class AiController extends BaseController {

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private WenXinYiYanService wenXinYiYanService;

    @PostMapping("/explain")
    public AjaxResult explain(@RequestBody Map<String, String> body) {
        String content = body.get("content");
        if (content == null || content.isEmpty()) {
            return AjaxResult.error("内容不能为空");
        }
        String result = deepSeekService.chat(content);
        return success(result);
    }

    @PostMapping("/wxyyExplain")
    public AjaxResult wxyyExplain(@RequestBody Map<String, String> body) {
        String content = body.get("content");
        if (content == null || content.isEmpty()) {
            return AjaxResult.error("内容不能为空");
        }
        return success(wenXinYiYanService.chat(content));
    }
}
