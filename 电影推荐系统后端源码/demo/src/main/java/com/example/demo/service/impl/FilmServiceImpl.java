package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.FilmMapper;
import com.example.demo.pojo.convert.FilmConvert;
import com.example.demo.pojo.convert.NoticeConvert;
import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.entity.Film;
import com.example.demo.pojo.entity.Thumb;
import com.example.demo.pojo.vo.FilmVO;
import com.example.demo.service.IFilmService;
import com.example.demo.service.IThumbService;
import com.example.demo.util.FilmType;
import com.example.demo.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("fileServiceImpl")
@Slf4j
public class FilmServiceImpl extends ServiceImpl<FilmMapper, Film>
        implements IFilmService {

    @Resource(name = "thumbServiceImpl")
    private IThumbService thumbService;


    @Override
    public ResultEntity listPage(FilmVO fileVO, Page<Film> iPage) {
        LambdaQueryWrapper<Film> filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filmLambdaQueryWrapper.like(Objects.nonNull(fileVO.getTitle()),Film::getTitle,fileVO.getTitle())
                .eq(!Objects.equals(fileVO.getEnabled(),-1),Film::getEnabled,fileVO.getEnabled())
                .and(Objects.nonNull(fileVO.getKeyword()),wq->wq.like(Film::getDirectors,fileVO.getKeyword())
                        .or().like(Film::getPlaywright,fileVO.getKeyword())
                        .or().like(Film::getActors,fileVO.getKeyword())
                        .or().like(Film::getTitle,fileVO.getKeyword()));
        if (Objects.equals(fileVO.getType(), FilmType.HOT_SCREENING)) {
            LocalDate endTIme = LocalDate.now();
            LocalDate startTime = endTIme.minusDays(30);
            filmLambdaQueryWrapper.le(Film::getReleaseTime,endTIme).ge(Film::getReleaseTime,startTime);

        } else if (Objects.equals(fileVO.getType(), FilmType.COMING_SOON)) {
            LocalDate now = LocalDate.now();
            filmLambdaQueryWrapper.and(i ->i.gt(Film::getReleaseTime,now).or().isNull(Film::getReleaseTime));
        } else if (Objects.equals(fileVO.getType(),FilmType.RELEASE)){
            LocalDate now = LocalDate.now();
            filmLambdaQueryWrapper.le(Film::getReleaseTime,now).orderByDesc(Film::getReleaseTime);
        }

        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(this.page(iPage,filmLambdaQueryWrapper).convert(item -> {
                FilmDTO filmDTO = FilmConvert.INSTANCE.entityConvertDTO(item);
                String REGEX_CHINESE = "[\u4e00-\u9fa5]|[(]|[)]|[:]";
                Pattern pat = Pattern.compile(REGEX_CHINESE);
                Matcher mat = pat.matcher(filmDTO.getReleaseTime());
                String temp = mat.replaceAll("");

                if (StringUtils.containsWhitespace(temp)) {
                    LocalDate releaseTime = LocalDate.parse(temp, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate now = LocalDate.now();
                    if (releaseTime.isBefore(now) && now.until(releaseTime, ChronoUnit.DAYS) <= 30L) {
                        filmDTO.setStatus("0");
                    } else if (releaseTime.isBefore(now)){
                        filmDTO.setStatus("2");
                    } else if (now.isBefore(releaseTime)) {
                        filmDTO.setStatus("1");
                    }
                }
                return filmDTO;
            }));
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
    public ResultEntity saveFilm(FilmVO fileVO) {
        Film film = FilmConvert.INSTANCE.voConvertEntity(fileVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            this.save(film);
            resultEntity.setData(film.getId());
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
    public ResultEntity updateFilm(FilmVO fileVO) {
        Film film = FilmConvert.INSTANCE.voConvertEntity(fileVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            this.updateById(film);
            resultEntity.setData(film.getId());
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
    public ResultEntity delFilm(String filmId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            Film film = this.getById(filmId);
            film.setEnabled(0);
            resultEntity.setData(updateById(film));
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
    public ResultEntity getFilmInfo(String filmId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            Film film = this.getById(filmId);
            resultEntity.setData(film);
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
    public ResultEntity recommendFilm(String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Film> recommendFilmList = new ArrayList<>();
            LambdaQueryWrapper<Thumb> thumbdaQueryWrap = new LambdaQueryWrapper<>();
            thumbdaQueryWrap.eq(Thumb::getUserId,userId);
            List<Thumb> thumbList = thumbService.list(thumbdaQueryWrap);
            List<String> filmIDs = new ArrayList<>();
            if (!CollectionUtils.isEmpty(thumbList)) {
                Map<String, List<Thumb>> thumbCollect = thumbList.stream().collect(Collectors.groupingBy(Thumb::getFilmId));
                Set<String> ids = thumbCollect.keySet();
                filmIDs = ids.stream().toList();
                LambdaQueryWrapper<Film> filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
                filmLambdaQueryWrapper.in(Film::getId,filmIDs)
                        .eq(Film::getEnabled,1);
                List<Film> filmList = this.list(filmLambdaQueryWrapper);
                Map<String,Integer> map = new HashMap<>();
                for (Film film : filmList) {
                    String[] types = film.getTypes().split(" ");
                    for (String type : types) {
                        Integer integer = map.get(type);
                        if (Objects.isNull(integer)) {
                            map.put(type,1);
                        } else {
                            map.put(type,map.get(type)+1);
                        }
                    }
                }
                List<Integer> sortFilmIds = map.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                Map<String, List<Film>> filmCollect = filmList.stream().collect(Collectors.groupingBy(Film::getId));

                filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
                String top1 = "";
                String top2 = "";
                String top3 = "";
                for (String key : map.keySet()) {
                    if (map.get(key) == sortFilmIds.get(0)) {
                        top1 = key;
                    }
                    if (map.get(key) == sortFilmIds.get(1)) {
                        top2 = key;
                    }
                    if (map.get(key) == sortFilmIds.get(2)) {
                        top3 = key;
                    }
                }
                filmLambdaQueryWrapper.like(Film::getTypes,sortFilmIds.get(0))
                        .notIn(Film::getId,top1)
                        .eq(Film::getEnabled,1)
                        .last("limit 10");
                List<Film> recommendFilmList1 = this.list(filmLambdaQueryWrapper);
                List<Film> recommendFilmList2 = new ArrayList<>();
                if (sortFilmIds.size()>2) {
                    filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    filmLambdaQueryWrapper.like(Film::getTypes,top2)
                            .notIn(Film::getId,filmCollect.keySet())
                            .eq(Film::getEnabled,1)
                            .last("limit 7");
                    recommendFilmList2 = this.list(filmLambdaQueryWrapper);
                }
                List<Film> recommendFilmList3 = new ArrayList<>();
                if (sortFilmIds.size()>3) {
                    filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    filmLambdaQueryWrapper.like(sortFilmIds.size()>3,Film::getTypes,top3)
                            .notIn(Film::getId,filmCollect.keySet())
                            .eq(Film::getEnabled,1)
                            .last("limit 3");
                    recommendFilmList3 = this.list(filmLambdaQueryWrapper);
                }

                recommendFilmList.addAll(recommendFilmList1);
                recommendFilmList.addAll(recommendFilmList2);
                recommendFilmList.addAll(recommendFilmList3);
                if (recommendFilmList.size() <20) {
                    int limit = 20 - recommendFilmList.size();
                    filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    filmLambdaQueryWrapper.notIn(Film::getId,filmCollect.keySet())
                            .eq(Film::getEnabled,1)
                            .orderByDesc(Film::getScore).last("limit " + limit);
                    recommendFilmList.addAll(this.list(filmLambdaQueryWrapper));
                }
            } else {
                LambdaQueryWrapper<Film> filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
                filmLambdaQueryWrapper.eq(Film::getEnabled,1)
                        .orderByDesc(Film::getScore).last("limit 20" );
                recommendFilmList.addAll(this.list(filmLambdaQueryWrapper));
            }
            resultEntity.setData(FilmConvert.INSTANCE.entitiesConvertListDTO(recommendFilmList));
            resultEntity.setCode("200");
            resultEntity.setMessage("获取成功");
            return resultEntity;
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }

    }

    @Override
    public ResultEntity top() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            LambdaQueryWrapper<Film> filmLambdaQueryWrapper = new LambdaQueryWrapper<>();
            filmLambdaQueryWrapper.eq(Film::getEnabled,1)
                    .orderByDesc(Film::getScore)
                    .last("limit 10");
            List<Film> list = this.list(filmLambdaQueryWrapper);
            if (CollectionUtils.isEmpty(list)) {
                resultEntity.setCode("555");
                resultEntity.setMessage("暂无电影数据");
                return resultEntity;
            }
            resultEntity.setCode("200");
            resultEntity.setData(list);
            resultEntity.setMessage("获取成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("获取失败");
            e.printStackTrace();
            return resultEntity;
        }
    }
}
