package com.std.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    List<People> peopleList;

    MainController () {
        peopleList = new ArrayList<>();
    }

    @GetMapping("/main/peopleList")
    @ResponseBody
    public List<People> peopleList () {
        return peopleList;
    }

    @GetMapping("/main/peopleAdd")
    @ResponseBody
    public String peopleAdd (String name, int age) {
        People people = new People(name, age);
        peopleList.add(people);
        return people.getId() + "번 사람이 추가되었습니다.";
    }

    @GetMapping("/main/peopleRemove")
    @ResponseBody
    public String peopleRemove (int id) {
        People foundPeople = peopleList
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (foundPeople == null) return id + "번 사람이 존재하지 않습니다.";

        peopleList.remove(foundPeople);

        return id + "번 사람이 삭제되었습니다.";
    }

    @GetMapping("/main/peopleModify")
    @ResponseBody
    public String peopleModify (int id, String name, int age) {
        People foundPeople = peopleList
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (foundPeople == null) return id + "번 사람이 존재하지 않습니다.";

        foundPeople.setName(name);
        foundPeople.setAge(age);

        return id + "번 사람이 수정되었습니다.";
    }
}

@Getter
@AllArgsConstructor
class People {
    private static int lastId;
    private int id;
    @Setter
    private String name;
    @Setter
    private int age;

    static {
        lastId = 0;
    }

    People (String name, int age) {
        this.id = ++lastId;
        this.name = name;
        this.age = age;
    }
}
