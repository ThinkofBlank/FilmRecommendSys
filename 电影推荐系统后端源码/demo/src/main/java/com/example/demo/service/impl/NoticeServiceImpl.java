package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.NoticeMapper;
import com.example.demo.pojo.convert.NoticeConvert;
import com.example.demo.pojo.entity.Notice;
import com.example.demo.pojo.vo.NoticeVO;
import com.example.demo.service.INoticeService;
import com.example.demo.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("noticeServiceImpl")
@Slf4j
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements INoticeService {



    @Override
    public ResultEntity infos(NoticeVO noticeVO) {
        LambdaQueryWrapper<Notice> noticeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeLambdaQueryWrapper.like(Objects.nonNull(noticeVO.getTitle()),Notice::getTitle,noticeVO.getTitle() );

        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Notice> list = this.list(noticeLambdaQueryWrapper);
            resultEntity.setData(NoticeConvert.INSTANCE.entitiesConvertListDTO(list));
            resultEntity.setCode("200");
            resultEntity.setMessage("获取成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }

    }

    @Override
    public ResultEntity saveNotice(NoticeVO noticeVO) {
        Notice notice = NoticeConvert.INSTANCE.voConvertEntity(noticeVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            notice.setNoticeTime(new Date());
            this.save(notice);
            resultEntity.setData(notice.getId());
            resultEntity.setCode("200");
            resultEntity.setMessage("保存成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity updateNotice(NoticeVO noticeVO) {
        Notice notice = NoticeConvert.INSTANCE.voConvertEntity(noticeVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            this.updateById(notice);
            resultEntity.setData(notice.getId());
            resultEntity.setCode("200");
            resultEntity.setMessage("更新成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity delNotice(String noticeId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(this.removeById(noticeId));
            resultEntity.setCode("200");
            resultEntity.setMessage("删除成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }
    @Override
    public ResultEntity getInfoByID(String noticeId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(this.getById(noticeId));
            resultEntity.setCode("200");
            resultEntity.setMessage("获取成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    
}
