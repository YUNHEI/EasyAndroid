//package com.chen.baseextend.bean.infomation;
//
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author alan
// * @date 2018/11/7
// */
//public class StringConverter implements PropertyConverter<List<String>, String> {
//
//    @Override
//    public List<String> convertToEntityProperty(String databaseValue) {
//        if (databaseValue == null) {
//            return null;
//        }
//        else {
//            List<String> list = Arrays.asList(databaseValue.split(","));
//            return new ArrayList<>(list);
//        }
//    }
//
//    @Override
//    public String convertToDatabaseValue(List<String> entityProperty) {
//        if(entityProperty==null){
//            return null;
//        }
//        else{
//            StringBuilder sb= new StringBuilder();
//            for(String link:entityProperty){
//                sb.append(link);
//                sb.append(",");
//            }
//            return sb.toString();
//        }
//    }
//}
