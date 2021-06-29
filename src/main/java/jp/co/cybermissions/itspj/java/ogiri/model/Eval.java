package jp.co.cybermissions.itspj.java.ogiri.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Eval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank
    @Size(max = 200)
    @Column(name ="choiceText", nullable = false )
    private String choiceText;

    @NotNull
    @Column(name ="comment", nullable = false )
    @Size(max = 200)
    private String comment;

    @NotNull
    @Column(name ="point", nullable = false )
    private Integer point;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
