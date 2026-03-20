package com.erp.hrms.Repository;

import com.erp.hrms.Entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("SELECT h FROM Holiday h " +
           "WHERE (:name IS NULL OR h.name LIKE %:name%) " +
           "AND (:holidayDate IS NULL OR h.holidayDate = :holidayDate) AND h.useYn = 'Y'"
            )
    List<Holiday> search(@Param("name") String name, @Param("holidayDate") LocalDate holidayDate);


    boolean existsByHolidayDate(LocalDate workDate);
}
