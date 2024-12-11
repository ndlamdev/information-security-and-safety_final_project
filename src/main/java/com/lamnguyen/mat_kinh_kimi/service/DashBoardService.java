package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.DashBoardRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.NumberList;

import java.time.YearMonth;
import java.util.*;

public class DashBoardService {
    private static DashBoardService instance;
    private String[] quarters = new String[]{"(1, 2, 3)", "(4, 5, 6)", "(7, 8, 9)", "(10, 11, 12)"};
    private List<String> categoryName, categoryGroupName;
    private final DashBoardRepositoryImpl DASH_BOARD_REPOSITORY;

    private DashBoardService() {
        DASH_BOARD_REPOSITORY = DashBoardRepositoryImpl.getInstance();
    }

    public static DashBoardService getInstance() {
        return (instance = instance == null ? new DashBoardService() : instance);
    }

    public Map<String, List<NumberList>> numberListByCategory(boolean category, boolean quarter, int year) {
        return category ? numberListByCategory(quarter, year) : numberListByCategoryGroup(quarter, year);
    }

    public Map<String, List<NumberList>> numberListByCategory(boolean quarter, int year) {
        updateCategoryName();

        return quarter ? numberListByCategoryByQuarter(year) : numberListCategoryByMonth(year);
    }

    public Map<String, List<NumberList>> numberListByCategoryGroup(boolean quarter, int year) {
        updateCategoryGroupName();

        return quarter ? numberListCategoryGroupByQuarter(year) : numberListCategoryGroupByMonth(year);
    }

    public Map<String, List<NumberList>> numberListCategoryGroupByMonth(int year) {
        List<NumberList> list = DASH_BOARD_REPOSITORY.numberListByCategoryGroupAndMonth(year);

        return formatNumberListByMonth(list, categoryGroupName);
    }

    public Map<String, List<NumberList>> numberListCategoryGroupByQuarter(int year) {
        List<NumberList> list = DASH_BOARD_REPOSITORY.numberListByCategoryGroupAndQuarter(year);

        return formatNumberListByQuarter(list, categoryGroupName);
    }

    public Map<String, List<NumberList>> numberListCategoryByMonth(int year) {
        List<NumberList> list = DASH_BOARD_REPOSITORY.numberListByCategoryAndMonth(year);
        return formatNumberListByMonth(list, categoryName);
    }

    public Map<String, List<NumberList>> numberListByCategoryByQuarter(int year) {
        List<NumberList> list = DASH_BOARD_REPOSITORY.numberListByCategoryAndQuarter(year);
        return formatNumberListByQuarter(list, categoryName);
    }

    public List<NumberList> numberListByProduct(int productId, int month, int year) {
        List<NumberList> list = DASH_BOARD_REPOSITORY.numberListByProduct(productId, month, year);
        YearMonth yearMonth = YearMonth.of(year, month);
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            for (NumberList nl : list) {
                if (nl.getTime() == day) continue;
            }

            NumberList number = new NumberList();
            number.setTime(day);
            number.setQuantity(0);
            list.add(number);
        }

        Collections.sort(list, sortByMonth());
        return list;
    }

    public Map<String, List<NumberList>> formatNumberListByMonth(List<NumberList> list, List<String> names) {
        Map<String, List<NumberList>> result = new HashMap<String, List<NumberList>>();
        for (NumberList nl : list) {
            List<NumberList> numberLists = result.get(nl.getName());
            numberLists = numberLists == null ? new ArrayList<NumberList>() : numberLists;
            numberLists.add(nl);
            result.put(nl.getName(), numberLists);
        }

        formatNumberListByName(result, names);

        for (Map.Entry<String, List<NumberList>> entry : result.entrySet()) {
            List<NumberList> numberLists = entry.getValue();
            month:
            for (int month = 1; month <= 12; month++) {
                for (NumberList nl : numberLists) {
                    if (nl.getTime() == month) continue month;
                }

                NumberList number = new NumberList();
                number.setTime(month);
                number.setQuantity(0);
                numberLists.add(number);
            }

            Collections.sort(numberLists, sortByMonth());
        }
        return result;
    }

    public Map<String, List<NumberList>> formatNumberListByQuarter(List<NumberList> list, List<String> names) {
        Map<String, List<NumberList>> result = new HashMap<String, List<NumberList>>();
        for (NumberList nl : list) {
            List<NumberList> numberLists = result.get(nl.getName());
            numberLists = numberLists == null ? new ArrayList<NumberList>() : numberLists;
            numberLists.add(nl);
            result.put(nl.getName(), numberLists);
        }

        formatNumberListByName(result, names);
        for (Map.Entry<String, List<NumberList>> entry : result.entrySet()) {
            List<NumberList> numberLists = entry.getValue();
            quarter:
            for (int quarter = 1; quarter <= 4; quarter++) {
                for (NumberList nl : numberLists) {
                    if (nl.getTime() == quarter) continue quarter;
                }

                NumberList number = new NumberList();
                number.setTime(quarter);
                number.setQuantity(0);
                numberLists.add(number);
            }

            Collections.sort(numberLists, sortByMonth());
        }
        return result;
    }

    public void formatNumberListByName(Map<String, List<NumberList>> map, List<String> names) {
        for (String name : names) if (!map.containsKey(name)) map.put(name, new ArrayList<NumberList>());
    }

    public void updateCategoryName() {
        categoryName = DASH_BOARD_REPOSITORY.listCategoryName();
    }

    public void updateCategoryGroupName() {
        categoryGroupName = DASH_BOARD_REPOSITORY.listCategoryGroupName();
    }

    private static Comparator sortByMonth() {
        return new Comparator<NumberList>() {
            @Override
            public int compare(NumberList o1, NumberList o2) {
                return o1.getTime() - o2.getTime();
            }
        };
    }
}
