package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.entity.Film;
import com.example.demo.pojo.vo.FilmVO;
import com.example.demo.service.IFilmService;
import com.example.demo.util.FilmUtil;
import com.example.demo.util.ResultEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("film")
public class FilmController {

    @Resource(name = "fileServiceImpl")
    private IFilmService filmService;

    @GetMapping("/list")
    public ResultEntity listPage (FilmVO fileVO, Page<Film> iPage){
        return filmService.listPage(fileVO, iPage);
    }

    @PostMapping("/save")
    public ResultEntity saveFilm (@RequestBody FilmVO fileVO){
        return filmService.saveFilm(fileVO);
    }

    @PostMapping("/update")
    public ResultEntity updateFilm (@RequestBody FilmVO fileVO){
        return filmService.updateFilm(fileVO);
    }

    @GetMapping("/del/{fileId}")
    public ResultEntity delFilm (@PathVariable("fileId") String fileId){
        return filmService.delFilm(fileId);
    }

    @GetMapping("/getFilmInfo/{fileId}")
    public ResultEntity getFilmInfo (@PathVariable("fileId") String fileId){
        return filmService.getFilmInfo(fileId);
    }

    @GetMapping("/getFilmResource")
    public void getFilmResource (){
        //爬虫时间很长，单独开一个线程后代进行
        try {
            List<Film> filmList = FilmUtil.getDataObjectList();
            for (Film film : filmList) {
                //判断爬到的数据是否已经存在，如果存在则更新，如果不存在则新建
                LambdaQueryWrapper<Film> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Film::getTitle,film.getTitle())
                        .eq(Film::getDirectors,film.getDirectors());
                Film one = filmService.getOne(lambdaQueryWrapper);
                if (Objects.isNull(one)) {
                    filmService.save(film);
                } else {
                    film.setId(one.getId());
                    filmService.updateById(film);
                }

                System.out.println(film + " ------> " + "保存成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/recommendFilm/{userId}")
    public ResultEntity recommendFilm(@PathVariable("userId")String userId) {
        return filmService.recommendFilm(userId);
    }

    @GetMapping("/top")
    public ResultEntity top(){
        return filmService.top();
    }

}
