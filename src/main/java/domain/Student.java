package domain;

import json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */

public class Student extends BasicStudent {
    List<Exam> exams;

    @SafeVarargs
    public Student(String name, String surname, Integer year, Tuple<String, Integer>... exams) {
        super(name, surname, year);
        this.exams = Arrays
                .stream(exams)
                .map(x -> new Exam(x.key, x.value))
                .collect(Collectors.toList());
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject res = super.toJsonObject();
        res.add(new JsonPair("exams", examsToJson()));
        return res;
    }

    private JsonArray examsToJson() {
        return new JsonArray(exams
                .stream()
                .map(Exam::toJson)
                .toArray(Json[]::new)
        );
    }

    public static class Exam {
        String course;
        int mark;
        Exam(String course, int mark) {
            this.course = course;
            this.mark = mark;
        }

        JsonObject toJson() {
            return new JsonObject(
                    new JsonPair("course", new JsonString(course)),
                    new JsonPair("mark", new JsonNumber(mark)),
                    new JsonPair("passed", new JsonBoolean(mark >= 3))
            );
        }
    }
}