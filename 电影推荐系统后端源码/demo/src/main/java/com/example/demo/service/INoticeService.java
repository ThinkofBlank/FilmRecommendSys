package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.dto.NoticeDTO;
import com.example.demo.pojo.entity.Notice;
import com.example.demo.pojo.vo.NoticeVO;
import com.example.demo.util.ResultEntity;

public interface INoticeService extends IService<Notice> {

    ResultEntity  infos(NoticeVO noticeVO);

    ResultEntity saveNotice (NoticeVO noticeVO);

    ResultEntity updateNotice (NoticeVO noticeVO);

    ResultEntity delNotice (String NoticeId);
    ResultEntity getInfoByID (String NoticeId);
}
