package com.csg.cms.domain;


import javax.persistence.*;

import com.csg.cms.enumerate.Status;
import com.csg.cms.enumerate.Type;
import lombok.Data;

@Entity
@Data
public class Article {
    @Id
    private int id;

    private String title;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Type type;

}