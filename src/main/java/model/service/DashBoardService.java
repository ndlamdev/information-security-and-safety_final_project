package model.service;

import model.DAO.DashBoardDAO;
import model.bean.NumberList;

import java.time.YearMonth;
import java.util.*;

public class DashBoardService {
    private static DashBoardService service;
    private String[] quarters = new String[]{"(1, 2, 3)", "(4, 5, 6)", "(7, 8, 9)", "(10, 11, 12)"};
    private List<String> categoryName, categoryGroupName;

    public static DashBoardService getInstance() {
        return (service = service == null ? new DashBoardService() : service);
    }

    public Map<String, List<NumberList>> numberListByCategory(boolean category, boolean quarter, int year) {
        if (category) return numberListByCategory(quarter, year);
        if (!category) return numberListByCategoryGroup(quarter, year);
        return null;
    }

    public Map<String, List<NumberList>> numberListByCategory(boolean quarter, int year) {
        updateCategoryName();

        if (quarter) return numberListByCategoryByQuarter(year);
        if (!quarter) return numberListCategoryByMonth(year);
        return null;
    }

    public Map<String, List<NumberList>> numberListByCategoryGroup(boolean quarter, int year) {
        updateCategoryGroupName();

        if (quarter) return numberListCategoryGroupByQuarter(year);
        if (!quarter) return numberListCategoryGroupByMonth(year);
        return null;
    }

    public Map<String, List<NumberList>> numberListCategoryGroupByMonth(int year) {
        List<NumberList> list = DashBoardDAO.getInstance().numberListByCategoryGroupAndMonth(year);

        return formatNumberListByMonth(list, categoryGroupName);
    }

    public Map<String, List<NumberList>> numberListCategoryGroupByQuarter(int year) {
        List<NumberList> list = DashBoardDAO.getInstance().numberListByCategoryGroupAndQuarter(year);

        return formatNumberListByQuarter(list, categoryGroupName);
    }

    public Map<String, List<NumberList>> numberListCategoryByMonth(int year) {
        List<NumberList> list = DashBoardDAO.getInstance().numberListByCategoryAndMonth(year);
        return formatNumberListByMonth(list, categoryName);
    }

    public Map<String, List<NumberList>> numberListByCategoryByQuarter(int year) {
        List<NumberList> list = DashBoardDAO.getInstance().numberListByCategoryAndQuarter(year);
        return formatNumberListByQuarter(list, categoryName);
    }

    public List<NumberList> numberListByProduct(int productId, int month, int year) {
        List<NumberList> list = DashBoardDAO.getInstance().numberListByProduct(productId, month, year);
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
        categoryName = DashBoardDAO.getInstance().listCategoryName();
    }

    public void updateCategoryGroupName() {
        categoryGroupName = DashBoardDAO.getInstance().listCategoryGroupName();
    }

    private static Comparator sortByMonth(){
        return new Comparator<NumberList>() {
            @Override
            public int compare(NumberList o1, NumberList o2) {
                return o1.getTime() - o2.getTime();
            }
        };
    }
}
