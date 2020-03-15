package com.indracompany.refis.contracts.model.entity;

import com.indracompany.refis.contracts.core.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "WS_PARAMETER")
public class WSParameter extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -4303294297690818245L;
    @Id
    @Column(name = "PARAM_KEY")
    private String key;
    @Column(name = "PARAM_VALUE")
    private String value;

}