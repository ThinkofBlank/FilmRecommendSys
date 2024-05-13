package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.ThumbDTO;
import com.example.demo.pojo.entity.Thumb;
import com.example.demo.pojo.vo.ThumbVO;
import com.example.demo.pojo.vo.UserVO;
import com.example.demo.service.IThumbService;
import com.example.demo.util.ResultEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("thumb")
public class ThumbController {

    @Resource(name = "thumbServiceImpl")
    private IThumbService thumbService;

    @GetMapping("/list")
    public ResultEntity listPage (ThumbVO thumbVO, Page<Thumb> iPage){
        return thumbService.listPage(thumbVO, iPage);
    }

    @PostMapping("/save")
    public ResultEntity saveThumb (@RequestBody ThumbVO thumbVO){
        return thumbService.saveThumb(thumbVO);
    }

    @PostMapping("/update")
    public ResultEntity updateThumb (@RequestBody ThumbVO thumbVO){
        return thumbService.updateThumb(thumbVO);
    }

    @GetMapping("/del/{thumbId}")
    public ResultEntity delThumb (@PathVariable("thumbId") String thumbId){
        return thumbService.delThumb(thumbId);
    }

    @GetMapping("/thumbNum/{filmId}")
    public ResultEntity thumbNum (@PathVariable("filmId") String filmId) {
        return thumbService.thumbNum(filmId);
    }

    @GetMapping("/getLike")
    public ResultEntity getLike(ThumbVO thumbVO) {
        return thumbService.getLike(thumbVO);
    }


}
