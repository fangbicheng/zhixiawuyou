package com.fang.zhixiawuyou.util;

import com.fang.zhixiawuyou.bean.Demand;
import com.fang.zhixiawuyou.bean.Internship;
import com.fang.zhixiawuyou.bean.Item;
import com.fang.zhixiawuyou.bean.Recruitment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/8/1.
 */
public class TransferUtil {

    public static List<Item> recruitmentToItem(List<Recruitment> recruitmentList) {
        List<Item> itemList = new ArrayList<>();
        for (Recruitment recruitment : recruitmentList) {
            Item item = new Item();
            item.setTitle(recruitment.getCompany());
            item.setDate(recruitment.getDate());
            item.setImageUrl(recruitment.getImage().getUrl());
            item.setLink(recruitment.getLink());
            item.setCategory("recruitment");
            itemList.add(item);
        }

        return itemList;
    }

    public static List<Recruitment> itemToRecruitment(List<Item> itemList) {
        List<Recruitment> recruitmentList = new ArrayList<>();
        for (Item item : itemList) {
            Recruitment recruitment = new Recruitment();
            recruitment.setCompany(item.getTitle());
            recruitment.setDate(item.getDate());
            recruitment.setImage(new BmobFile(new File(item.getImageUrl())));
            recruitment.setLink(item.getLink());
            recruitmentList.add(recruitment);
        }

        return recruitmentList;
    }

    public static List<Item> internshipToItem(List<Internship> internshipList) {
        List<Item> itemList = new ArrayList<>();
        for (Internship internship : internshipList) {
            Item item = new Item();
            item.setTitle(internship.getCompany());
            item.setDate(internship.getDate());
            item.setImageUrl(internship.getImage().getUrl());
            item.setLink(internship.getLink());
            item.setCategory("internship");
            itemList.add(item);
        }

        return itemList;
    }

    public static List<Internship> itemToInternship(List<Item> itemList) {
        List<Internship> internshipList = new ArrayList<>();
        for (Item item : itemList) {
            Internship internship = new Internship();
            internship.setCompany(item.getTitle());
            internship.setDate(item.getDate());
            internship.setImage(new BmobFile(new File(item.getImageUrl())));
            internship.setLink(item.getLink());
            internshipList.add(internship);
        }

        return internshipList;
    }

    public static List<Item> demandToItem(List<Demand> demandList) {
        List<Item> itemList = new ArrayList<>();
        for (Demand demand : demandList) {
            Item item = new Item();
            item.setTitle(demand.getCompany());
            item.setDate(demand.getDate());
            item.setImageUrl(demand.getImage().getUrl());
            item.setLink(demand.getLink());
            item.setCategory("demand");
            itemList.add(item);
        }

        return itemList;
    }

    public static List<Demand> itemToDemand(List<Item> itemList) {
        List<Demand> demandList = new ArrayList<>();
        for (Item item : itemList) {
            Demand demand = new Demand();
            demand.setCompany(item.getTitle());
            demand.setDate(item.getDate());
            demand.setImage(new BmobFile(new File(item.getImageUrl())));
            demand.setLink(item.getLink());
            demandList.add(demand);
        }

        return demandList;
    }
}
