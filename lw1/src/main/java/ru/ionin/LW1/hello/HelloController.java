package ru.ionin.LW1.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    private final List<String> arrayList = new ArrayList<>();
    private final Map<Integer, String> hashMap = new HashMap<>();
    private int mapKeyCounter = 1;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "Word") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam("s") String s) {
        arrayList.add(s);
        return "Добавлено в ArrayList: " + s;
    }

    @GetMapping("/show-array")
    public List<String> showArrayList() {
        return arrayList;
    }

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam("s") String s) {
        hashMap.put(mapKeyCounter++, s);
        return "Добавлено в HashMap: " + s;
    }

    @GetMapping("/show-map")
    public Map<Integer, String> showHashMap() {
        return hashMap;
    }

    @GetMapping("/show-all-length")
    public String showAllLength() {
        return String.format(
                "ArrayList содержит %d элементов, HashMap содержит %d элементов",
                arrayList.size(), hashMap.size()
        );
    }
}
