package com.example.spring_boot_booking_hotel.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
@MappedSuperclass
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class GennericEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate crated_date;
    private  LocalDate updated_date;
    private LocalDate deleted_date;
  public  GennericEntity(){
      this.crated_date = LocalDate.now();
      this.updated_date = LocalDate.now();
      this.deleted_date = LocalDate.now();
  }
}
