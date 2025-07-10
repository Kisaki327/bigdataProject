package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.impl.DeepSeekService;
import com.ruoyi.system.service.impl.RedisCacheService;
import com.ruoyi.system.service.impl.WenXinYiYanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/system/ai")
@Tag(name = "AI 图表解读接口", description = "基于大模型的图表语义分析能力")
public class AiController extends BaseController {

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private WenXinYiYanService wenXinYiYanService;

    @Autowired
    private RedisCacheService redisCacheService;

    @PostMapping("/explain")
    @Operation(
            summary = "调用 DeepSeek AI 图表解读",
            description = "传入图表内容字符串，返回 DeepSeek 模型的智能解释结果",
            requestBody = @RequestBody(
                    description = "请求体参数，包含 content 字段",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"content\": \"图表描述内容\"}"
                            )
                    )
            )
    )
    public AjaxResult explain(@org.springframework.web.bind.annotation.RequestBody Map<String, String> body) {
        String content = body.get("content");
        if (content == null || content.isEmpty()) {
            return AjaxResult.error("内容不能为空");
        }
        String result = deepSeekService.chat(content);
        return success(result);
    }

    @PostMapping("/wxyyExplain")
    @Operation(
            summary = "调用 文心一言 AI 图表解读",
            description = "传入图表内容字符串，返回百度文心一言模型的解释结果",
            requestBody = @RequestBody(
                    description = "请求体参数，包含 content 字段",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"content\": \"图表描述内容\"}"
                            )
                    )
            )
    )
    public AjaxResult wxyyExplain(@org.springframework.web.bind.annotation.RequestBody Map<String, String> body) {
        String content = body.get("content");
        String chartId = body.get("chartId");
        String key = "nba:ai:" + chartId;
        String result = redisCacheService.getOrCache(
                key,
                String.class,
                () -> wenXinYiYanService.chat(content),
                Duration.ofMinutes(30)
        );
        return success(result);
    }
}
