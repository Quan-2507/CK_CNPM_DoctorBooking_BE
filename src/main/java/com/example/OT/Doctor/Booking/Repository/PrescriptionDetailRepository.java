package com.example.OT.Doctor.Booking.Repository;

import com.example.OT.Doctor.Booking.Entity.PrescriptionDetail;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Long> {

    @Query("SELECT pd FROM PrescriptionDetail pd JOIN FETCH pd.medicine WHERE pd.prescription.id = :prescriptionId")
    List<PrescriptionDetail> findAllByPrescriptionId(@Param("prescriptionId") Long prescriptionId);
    List<PrescriptionDetail> findByPrescriptionId(Long prescriptionId);


}