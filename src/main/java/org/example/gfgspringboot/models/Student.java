package org.example.gfgspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.example.gfgspringboot.annotations.JsonSerializableField;

@Data
public class Student {
    @NonNull
    @JsonSerializableField(value="studentId")
    public int id;
    @NonNull
    public String name;

    public Student(@NonNull int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public String getUpperCaseName(String title){
        return title.toUpperCase() + this.name.toUpperCase();
    }
}
