package com.anudip.Agro_project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class RecommendationService {
    private SessionFactory sessionFactory;
    private Scanner sc;

    public RecommendationService() {
        this.sessionFactory = new Configuration().configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Region.class)
                .addAnnotatedClass(Recommendation.class)
                .addAnnotatedClass(Crop.class)
                .addAnnotatedClass(WeatherData.class)
                .buildSessionFactory();
        this.sc = new Scanner(System.in);
    }

    public void recommendationCall() {
        while (true) {
            User user = createUser();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Region region = CreateRegion(session);
            user.setRegion(region);

            WeatherData weatherData = createWeatherData(region);
            session.save(region);
            session.save(user);
            session.save(weatherData);

            String recommendationText = getRecommendationText(weatherData, region);
            if (recommendationText != null) {
                Recommendation recommendation = new Recommendation();
                recommendation.setRecommendationText(recommendationText);
                recommendation.setRegion(region);
                recommendation.setUser(user);

                session.save(recommendation);
                transaction.commit();
                System.out.println(" The Recommendation is based on your input data  : " + recommendationText);
            } else {
                System.out.println("No suitable recommendations found.");
                transaction.rollback(); 
            }

            session.close();
        }
    }

    private User createUser() {
        System.out.println("\n\t\t\t\t"+"\"WELCOME TO AGRO-GENIUS:\"");
        System.out.println("<------------------------------------------------------------------------------------------------>"+"\n");
        User user = new User();
        System.out.println("Please enter your name: ");
        user.setUser_name(sc.nextLine().trim());

        System.out.println("Please enter your password: ");
        user.setPassword(sc.nextLine().trim());

        System.out.println("Please enter your phone number: ");
        user.setPhoneNumber(sc.nextLine().trim());

        return user;
    }

    private Region CreateRegion(Session session) {
        Region region = new Region();
        System.out.print("Please enter the region name: ");
        String regionName = sc.nextLine().trim();
        region.setRegionName(regionName);
        System.out.println("Region selected: " + region.getRegionName());
        return region;
    }

    private WeatherData createWeatherData(Region region) {
        WeatherData wd = new WeatherData();
        wd.setRegion(region);

        System.out.print("Please enter weather condition eg..rainy,sunny,dry etc.....(or type 'exit' to quit): ");
        String condition = sc.nextLine().trim();
        if (condition.equalsIgnoreCase("exit")) {      	
            System.exit(0);
           
        }
        wd.setWeatherCondition(condition);

        System.out.print("Please enter temperature: ");
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a numeric value for temperature.");
            sc.next();
        }
        wd.setTemperature(sc.nextDouble());
        sc.nextLine(); 
        System.out.print("Please enter soil type: ");
        wd.setSoilType(sc.nextLine().trim());
        return wd;
    }

    private String getRecommendationText(WeatherData wd, Region region) {
        String condition = wd.getWeatherCondition().toLowerCase();
        double temperature = wd.getTemperature();
        String soilType = wd.getSoilType().toLowerCase();
     

        switch (condition) {
            case "rainy":
            
                if (temperature >= 20 && temperature <= 35 && soilType.equals("clay")) {
                    return "Suitable conditions detected for rice cultivation.";
                }
              
                else if (temperature >= 5 && temperature <= 19 && soilType.equals("clay")) {
                    return "Suitable conditions detected for sweet-potato,carrot cultivation.";
                }             
                break;

            case "sunny":
                if (temperature >= 10 && temperature <= 25 && soilType.equals("loamy")) {
                    return "Sunny weather and loamy soil are perfect for wheat cultivation.";
                } else if (temperature >= 26 && temperature <= 35 && soilType.equals("clay")) {
                    return "Suitable conditions detected for rice cultivation.";
                }
                break;

            case "dry":
                if (temperature >= 25 && temperature <= 40 && soilType.equals("sandy")) {
                    return "Dry conditions and sandy soil are suitable for maize cultivation.";
                } else if (temperature >= 30 && temperature <= 45 && soilType.equals("sandy")) {
                    return "Very dry conditions favor crops like millet and sorghum.";
                }
                break;

            case "humid":
                if (temperature >= 25 && temperature <= 35 && soilType.equals("silty")) {
                    return "Humid weather and silty soil are ideal for sugarcane cultivation.";
                } else if (temperature >= 22 && temperature <= 30 && soilType.equals("clay")) {
                    return "Humid conditions are good for growing taro.";
                }
                break;

            case "cloudy":
                if (temperature >= 18 && temperature <= 28 && soilType.equals("sandy")) {
                    return "Cloudy weather with sandy soil is suitable for growing root vegetables like carrots.";
                } else if (temperature >= 15 && temperature <= 25 && soilType.equals("loamy")) {
                    return "Cloudy conditions are ideal for growing leafy greens.";
                }
                break;

            case "frosty":
                if (temperature >= 5 && temperature <= 20 && (soilType.equals("loamy") || soilType.equals("clay"))) {
                    return "Frosty conditions are suitable for growing winter crops like kale or rye.";
                } else if (temperature < 5 && soilType.equals("sandy")) {
                    return "Frosty conditions can favor hardy crops like garlic.";
                }
                break;

            case "windy":
                if (temperature >= 15 && temperature <= 25 && (soilType.equals("silty") || soilType.equals("clay"))) {
                    return "Windy conditions can favor crops like barley, which are more resilient to wind stress.";
                } else if (temperature >= 20 && temperature <= 30 && soilType.equals("sandy")) {
                    return "Windy weather is good for growing drought-tolerant plants like succulents.";
                }
                break;

            case "overcast":
                if (temperature >= 12 && temperature <= 25 && soilType.equals("loamy")) {
                    return "Overcast conditions are ideal for growing leafy greens such as spinach or Swiss chard.";
                } else if (temperature >= 15 && temperature <= 22 && soilType.equals("silty")) {
                    return "Overcast weather favors crops like peas and beans.";
                }
                break;

            case "stormy":
                if (temperature >= 15 && temperature <= 28 && soilType.equals("sandy")) {
                    return "Stormy weather is suitable for planting resilient crops like sweet potatoes.";
                } else if (temperature >= 20 && temperature <= 30 && soilType.equals("loamy")) {
                    return "Stormy conditions can also support crops like corn, which thrive with moisture.";
                }
                break;

            case "hazy":
                if (temperature >= 18 && temperature <= 30 && soilType.equals("sandy")) {
                    return "Hazy weather is good for growing crops like chickpeas.";
                } else if (temperature >= 20 && temperature <= 28 && soilType.equals("silty")) {
                    return "Hazy conditions can benefit crops like quinoa.";
                }
                break;

            case "hot":
                if (temperature >= 25 && temperature <= 35 && soilType.equals("sandy")) {
                    return "Hot weather is ideal for cultivating drought-resistant crops like eggplants or peppers.";
                } else if (temperature >= 30 && temperature <= 40 && soilType.equals("sandy")) {
                    return "Extreme heat conditions are suitable for growing cactus fruits.";
                }
                break;

            case "cool":
                if (temperature >= 10 && temperature <= 20 && soilType.equals("loamy")) {
                    return "Cool weather is favorable for crops like peas and carrots.";
                } else if (temperature >= 5 && temperature <= 15 && soilType.equals("silty")) {
                    return "Cool conditions are great for planting garlic and onions.";
                }
                break;

            case "dry and hot":
                if (temperature >= 30 && temperature <= 40 && soilType.equals("sandy")) {
                    return "Dry and hot conditions are suitable for drought-resistant crops such as millet or quinoa.";
                } else if (temperature >= 35 && temperature <= 45 && soilType.equals("sandy")) {
                    return "Very hot and dry conditions are ideal for growing okra.";
                }
                break;

            case "misty":
                if (temperature >= 15 && temperature <= 25 && soilType.equals("silty")) {
                    return "Misty conditions can benefit crops like blueberries.";
                } else if (temperature >= 10 && temperature <= 20 && soilType.equals("clay")) {
                    return "Misty weather is also good for growing ferns.";
                }
                break;

            case "chilly":
                if (temperature >= 5 && temperature <= 15 && soilType.equals("loamy")) {
                    return "Chilly weather can be perfect for growing hardy vegetables like kale.";
                } else if (temperature >= 0 && temperature <= 10 && soilType.equals("sandy")) {
                    return "Chilly conditions are favorable for growing cold-hardy herbs like parsley.";
                }
                break;

            case "tropical":
                if (temperature >= 20 && temperature <= 30 && soilType.equals("silty")) {
                    return "Tropical conditions are ideal for growing bananas.";
                }
                break;

            case "arid":
                if (temperature >= 30 && temperature <= 45 && soilType.equals("sandy")) {
                    return "Arid conditions favor plants like cacti and succulents.";
                }
                break;
            default:
                return "no perfect crop is found on you given data input............".toUpperCase(); 
               
        }
        
        return "No suitable conditions detected"; 
        
    }

    public void closeResources() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        if (sc != null) {
            sc.close();
        }
    }
}
