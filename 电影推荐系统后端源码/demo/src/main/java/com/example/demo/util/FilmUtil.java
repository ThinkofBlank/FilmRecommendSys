package com.example.demo.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.pojo.entity.Film;
import com.example.demo.service.IFilmService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FilmUtil {

    @Autowired
    private IFilmService filmService;

    public static List<String> getUrls() throws IOException, InterruptedException {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                .build();
        String url = "https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&start=%s";
        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpResponse response = null;
        List<String> urlList = new ArrayList<>();
        HttpGet httpGet = null;
        //控制一次爬多少个
        for (int i = 0; i <= 100; i += 20) {
            String format = String.format(url, i);
            Thread.sleep(8000);
            httpGet = new HttpGet(format);
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("Origin", "https://movie.douban.com");
            httpGet.setHeader("Referer", "https://movie.douban.com");
            httpGet.setHeader("Cookie", "ll=\"118186\"; bid=LkK9GbnfUYA; viewed=\"27198978_26938710\"; douban-fav-remind=1; ct=y; dbcl2=\"237316588:O1pgIUvpYf8\"; push_doumail_num=0; ck=Ix5A; frodotk=\"dd910f2936f4850d98b32d7ba4dd8f27\"; push_noty_num=0");
            response = httpClient.execute(httpGet);
            JsonNode jsonNode = mapper.readTree(EntityUtils.toString(response.getEntity()));
            for (JsonNode value : jsonNode.get("data").findValues("url")) {
                urlList.add(value.asText());
            }
        }
        try {
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlList;
    }

    public static   List<Film> getDataObjectList() throws IOException, InterruptedException {
        List<String> urlList = getUrls();
        List<Film> filmList = new ArrayList<Film>();
        for (String u : urlList) {
            Document document = Jsoup.connect(u).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36").get();
            String title = document.select("#content > h1").text();
            String imageUrl = document.select("#mainpic > a > img").attr("src");
            String directors = document.select("#info > span:nth-child(1) > span.attrs > a").text();
            String playwright = document.select("#info > span:nth-child(3) > span.attrs > a").text();
            String actors = document.select("#info > span.actor > span.attrs").text();
            String types = document.select("div#info > span[property=v:genre]").text();
            String text = document.select("#info > span:nth-child(17)").text();
            if (StringUtils.isEmpty(text)) {
                text = document.select("#info > span:nth-child(19)").text();
            }
            String releaseTime = text;
            String score = document.select("#interest_sectl > div.rating_wrap.clearbox > div.rating_self.clearfix > strong").text();
            String introduction = document.select("#link-report-intra > span:nth-child(1)").text();
            Film film = new Film();
            film.setTitle(title);
            film.setImageUrl(imageUrl);
            film.setDirectors(directors);
            film.setPlaywright(playwright);
            film.setActors(actors);
            film.setTypes(types);
            film.setReleaseTime(releaseTime);
            film.setScore(score);
            film.setIntroduction(introduction);
            film.setEnabled(1);
            filmList.add(film);
            Thread.sleep(8000);
            System.out.println(u + " ------> " + "爬取成功!");
        }
        return filmList;
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        List<Film> filmList = FilmUtil.getDataObjectList();
//        for (Film film : filmList) {
//            //判断爬到的数据是否已经存在，如果存在则更新，如果不存在则新建
//            LambdaQueryWrapper<Film> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(Film::getTitle,film.getTitle())
//                            .eq(Film::getDirectors,film.getDirectors());
//            Film one = filmService.getOne(lambdaQueryWrapper);
//            if (Objects.isNull(one)) {
//                filmService.save(film);
//            } else {
//                film.setId(one.getId());
//                filmService.updateById(film);
//            }
//
//            System.out.println(film + " ------> " + "保存成功!");
//        }
//    }

}
