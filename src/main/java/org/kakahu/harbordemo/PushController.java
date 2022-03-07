package org.kakahu.harbordemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;

@Controller
public class PushController {

    @Value("${filepath}")
    private String filepath;


    @Autowired
    private HarborUtil har;

    @RequestMapping("/")
    public ModelAndView main() {
        //System.out.println("nnn");
        ModelAndView push_page = new ModelAndView("push");
        return push_page;
    }

    @PostMapping(value = "/up")
    public ModelAndView uploading(@RequestParam("uploadFile") MultipartFile uploadFile, Model model, @RequestParam("app_name")String app_name, @RequestParam("type")String type) {
        ModelAndView push = new ModelAndView("push");
        //ModelAndView mvLogin = new ModelAndView("up");
        File targetFile = new File(filepath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(filepath + uploadFile.getOriginalFilename());){
            out.write(uploadFile.getBytes());
            String[] commands;
            if ("1".equals(type)){
                commands = new String[] { "./fe.sh" , uploadFile.getOriginalFilename(), app_name};
            }else{
                commands = new String[] { "./be.sh" , uploadFile.getOriginalFilename(), app_name};
            }

            Command.exec("commands", commands);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("up_info","uploading failure");
            return push;
        }

        //System.out.println("app"+app_name+"&==>"+file.getOriginalFilename()+"==type==>"+type);
        model.addAttribute("up_info","uploading success");


        return push;
    }


    @PostMapping(value = "/bu")
    @ResponseBody//只返回字符串，而不是视图
    public String bu(@RequestParam("app_name")String app_name,@RequestParam("image")String image,@RequestParam("tag")String tag)  {
        String sss="";
        if("".equals(image) || null == image ){
            image=app_name;
            //System.out.println(123+image.toString());
        }
        try{
            String[] commands = new String[] { "./build.sh" ,app_name,image,tag };
            Command.exec("commands", commands);
            String RES_INFO=har.getTag(image,tag);
            if("error".equals(RES_INFO)){
                sss="构建并推送镜像失败";
            }else{
                sss="构建并推送镜像成功！推送的版本号为："+app_name+":"+RES_INFO;
            }
            //sss="构建测试消息;";
        }catch (Exception e){
            //model.addAttribute("buid_info","build failure");
            sss="构建过程中出现异常";

        }


        return sss;
    }



}
