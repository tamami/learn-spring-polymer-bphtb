package lab.aikibo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tamami on 04/02/17.
 */
@Entity
@Table(name = "dat_login")
@Data
public class User {

    @Id
    private String nmLogin;
    @Column
    private String nip;
    @Column
    private String password;

}
