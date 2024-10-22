package com.anudip.Agro_project;


public class MainClass {
    public static void main(String[] args) {
 
        RecommendationService recommendationService = new RecommendationService();
        
        try {
            recommendationService.recommendationCall(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}