package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.NoticeDTO;
import com.example.demo.pojo.entity.Notice;
import com.example.demo.pojo.vo.NoticeVO;
import com.example.demo.service.INoticeService;
import com.example.demo.util.ResultEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("notice")
public class NoticeController {

    @Resource(name = "noticeServiceImpl")
    private INoticeService noticeService;

    @GetMapping("/list")
    public ResultEntity infos (NoticeVO noticeVO){
        return noticeService.infos(noticeVO);
    }

    @PostMapping("/save")
    public ResultEntity saveNotice (@RequestBody NoticeVO noticeVO){
        return noticeService.saveNotice(noticeVO);
    }

    @PostMapping("/update")
    public ResultEntity updateNotice (@RequestBody NoticeVO noticeVO){
        return noticeService.updateNotice(noticeVO);
    }

    @GetMapping("/del/{noticeId}")
    public ResultEntity delThumb (@PathVariable("noticeId") String noticeId){
        return noticeService.delNotice(noticeId);
    }

    @GetMapping("/get/{noticeId}")
    public ResultEntity getInfoByID (@PathVariable("noticeId") String noticeId){
        return noticeService.getInfoByID(noticeId);
    }


}
