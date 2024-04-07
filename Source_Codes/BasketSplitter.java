package com.ocado.basket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class BasketSplitter {

    protected Map<String, List<String>> Product_delivery_option = new LinkedHashMap<String, List<String>>();
    protected Map<String, List<String>> Basket = new HashMap<String, List<String>>();

    public BasketSplitter(String absolutePathToConfigFile) throws IOException {

        try {
            Product_delivery_option = new ObjectMapper().readValue(new File(absolutePathToConfigFile),
                    LinkedHashMap.class);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

        // Initializing map of deliveries for products
        for (Map.Entry<String, List<String>> entry : Product_delivery_option.entrySet()) {
            if (Basket.size() == 10)
                break;
            for (String delivery_option : entry.getValue()) {
                if (Basket.isEmpty())
                    Basket.put(delivery_option, new ArrayList<>(0));
                else if (!Basket.containsKey(delivery_option))
                    Basket.put(delivery_option, new ArrayList<>(0));
            }
        }
    }

    public Map<String, List<String>> split(List<String> items) {

        // Filling in map of deliveries for products
        for (String item : items) {
            for (Map.Entry<String, List<String>> product : Product_delivery_option.entrySet()) {
                if (item.equals(product.getKey())) {
                    for (String delivery_type : product.getValue()) {
                        for (Map.Entry<String, List<String>> delivery_option_basket : Basket.entrySet()) {
                            if (delivery_type.equals(delivery_option_basket.getKey())) {
                                delivery_option_basket.getValue().add(item);
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Creating new Basket to be returned
        Map<String, List<String>> Basket_optimized = new HashMap<String, List<String>>();

        // Sorting products by the amount of available delivery types
        List<Map.Entry<String, List<String>>> sortedProducts = new ArrayList<>(Basket.entrySet());
        sortedProducts.sort((entry1, entry2) -> entry2.getValue().size() - entry1.getValue().size());

        for (Map.Entry<String, List<String>> entry : sortedProducts) {
            String deliveryType = entry.getKey();
            List<String> availableProducts = entry.getValue();

            // Creating delivery group for every delivery type
            List<String> groupProducts = new ArrayList<>();
            Basket_optimized.put(deliveryType, groupProducts);

            // Iterating through products for every delivery type
            for (String product : availableProducts) {
                // Checking if product is on the delivery list
                if (items.contains(product)) {
                    groupProducts.add(product);
                    items.remove(product);
                }
            }
        }

        // Removing empty values from the map
        Iterator<Map.Entry<String, List<String>>> iterator = Basket_optimized.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> optimizingBasket = iterator.next();
            if (optimizingBasket.getValue().isEmpty())
                iterator.remove();
        }

        return Basket_optimized;
    }

    public String SerializedJson(Map<String, List<String>> map) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map);
    }

    public static void main(String[] args) throws JsonGenerationException, JsonProcessingException, IOException,
            StreamReadException, DatabindException {
        BasketSplitter splitter = new BasketSplitter("C:/Users/wotmi/Desktop/Zadanie/config.json");
        System.out.println(splitter.SerializedJson(splitter.split(new ObjectMapper().readValue(
                new File(splitter.getClass().getClassLoader().getResource("basket-1.json").getFile()),
                new TypeReference<List<String>>() {
                }))));
        System.out.println(splitter.SerializedJson(splitter.split(new ObjectMapper().readValue(
                new File(splitter.getClass().getClassLoader().getResource("basket-2.json").getFile()),
                new TypeReference<List<String>>() {
                }))));
    }
}