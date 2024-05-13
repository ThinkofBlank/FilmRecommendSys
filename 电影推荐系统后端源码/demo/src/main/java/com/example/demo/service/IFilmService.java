package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.entity.Film;
import com.example.demo.pojo.vo.FilmVO;
import com.example.demo.util.ResultEntity;

import java.util.List;

public interface IFilmService extends IService<Film> {

    ResultEntity  listPage(FilmVO filmVO, Page<Film> iPage);

    ResultEntity saveFilm (FilmVO filmVO);

    ResultEntity updateFilm (FilmVO filmVO);

    ResultEntity delFilm (String filmId);
    ResultEntity getFilmInfo (String filmId);
    ResultEntity recommendFilm (String userId);
    ResultEntity top ();
}
