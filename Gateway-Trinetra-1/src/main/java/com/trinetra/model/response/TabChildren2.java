package com.trinetra.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabChildren2 {
    String name;
    Boolean checked;
    List<Children> children;
}
