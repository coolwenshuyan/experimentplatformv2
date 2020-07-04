package com.coolwen.experimentplatformv2.controller.HomepagesettingController;

import com.coolwen.experimentplatformv2.utils.FileUtil;
import com.coolwen.experimentplatformv2.utils.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: experimentplatform
 * @Package: com.coolwen.experimentplatformv2.controller.HomepagesettingController
 * @ClassName: FileController
 * @Author: Txc
 * @Description: 富文本编辑器
 * @Date: 2020/5/14 0014 12:22
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/file")
public class RichTextEditorController {
    @Value("${web.ExpModelImage-path}")
    private String expModelImage ;

    @ResponseBody
    @RequestMapping("/upload")
    public Message upload(@RequestParam("myFileName") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> object = new HashMap();
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        try {
            FileUtil.uploadFile(file.getBytes(), expModelImage, fileName);
            object.put("path", expModelImage +"/"+ fileName);
            object.put("url","/ExperimentPlatform/ExpModelImage/"+fileName);
            return Message.ok().put("data",object);
        } catch (Exception e) {
            return Message.error();
        }
    }
}
