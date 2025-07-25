package com.simply.service;

import com.simply.model.Home;
import com.simply.model.HomeCategory;

import java.util.List;

public interface HomeService {

    Home creatHomePageData(List<HomeCategory> categories);

}
