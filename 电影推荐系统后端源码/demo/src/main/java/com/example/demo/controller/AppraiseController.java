package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.AppraiseDTO;
import com.example.demo.pojo.entity.Appraise;
import com.example.demo.pojo.vo.AppraiseVO;
import com.example.demo.service.IAppraiseService;
import com.example.demo.util.FilmUtil;
import com.example.demo.util.ResultEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("appraise")
public class AppraiseController {

    @Resource(name = "appraiseServiceImpl")
    private IAppraiseService appraiseService;

    @GetMapping("/list")
    public ResultEntity listPage (AppraiseVO appraiseVO, Page<Appraise> iPage){
        return appraiseService.listPage(appraiseVO, iPage);
    }

    @PostMapping("/save")
    public ResultEntity saveAppraise (@RequestBody AppraiseVO appraiseVO){
        return appraiseService.saveAppraise(appraiseVO);
    }

    @PostMapping("/update")
    public ResultEntity updateAppraise (@RequestBody AppraiseVO appraiseVO){
        return appraiseService.updateAppraise(appraiseVO);
    }

    @GetMapping("/del/{appraiseId}")
    public ResultEntity delAppraise (@PathVariable("appraiseId") String appraiseId){
        return appraiseService.delAppraise(appraiseId);
    }


}
