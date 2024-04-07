package com.ocado.basket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

class BasketSplitterTest {
    @Test
    public void WrongPath_test() {
        try {
            new BasketSplitter("");
        } catch (IOException e) {
            assertEquals("Empty file path", e.getMessage());
        }
    }

    @Test
    public void JsonDeserialization_test() throws IOException {

        BasketSplitter instance = new BasketSplitter(
                this.getClass().getClassLoader().getResource("test-config.json").getFile());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"Coffee - Colombian, Whole Bean\":[\"Next day shipping\",\"Same day delivery\",\"In-store pick-up\",\"Parcel locker\"],\"Chocolate - Dark, 70% Cacao\":[\"Same day delivery\",\"Express Collection\",\"Courier\",\"Mailbox delivery\"],\"Apples - Granny Smith, Organic\":[\"Next day shipping\",\"In-store pick-up\",\"Parcel locker\"],\"Chicken - Boneless, Skinless Breast\":[\"Next day shipping\",\"In-store pick-up\",\"Courier\"],\"Yogurt - Greek, Plain\":[\"In-store pick-up\",\"Parcel locker\"]}";
        Object json_obj = mapper.readValue(jsonString, Object.class);
        String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_obj);
        assertEquals(prettyJsonString, instance.SerializedJson(instance.Product_delivery_option));
    }

    @Test
    public void Deliveriesmap_test() throws IOException {

        Set<String> delivery_types = new HashSet<>();
        delivery_types.add("Next day shipping");
        delivery_types.add("Same day delivery");
        delivery_types.add("In-store pick-up");
        delivery_types.add("Parcel locker");
        delivery_types.add("Express Collection");
        delivery_types.add("Courier");
        delivery_types.add("Mailbox delivery");

        BasketSplitter instance = new BasketSplitter(
                this.getClass().getClassLoader().getResource("test-config.json").getFile());
        assertTrue(delivery_types.equals(instance.Basket.keySet()));
    }

    @Test
    public void Splitter_test() throws IOException {

        BasketSplitter instance = new BasketSplitter(
                this.getClass().getClassLoader().getResource("test-config.json").getFile());

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"In-store pick-up\": [\"Chicken - Boneless, Skinless Breast\", \"Yogurt - Greek, Plain\", \"Apples - Granny Smith, Organic\"]}";
        Object json_obj = mapper.readValue(jsonString, Object.class);
        String prettyJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_obj);
        Map<String, List<String>> expectedMap = new HashMap<String, List<String>>();
        expectedMap = instance.split(
                mapper.readValue(new File(this.getClass().getClassLoader().getResource("test-basket.json").getFile()),
                        new TypeReference<List<String>>() {
                        }));

        assertEquals(prettyJsonString, instance.SerializedJson(expectedMap));
    }
}
