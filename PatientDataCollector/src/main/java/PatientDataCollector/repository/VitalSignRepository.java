package PatientDataCollector.repository;

import PatientDataCollector.entity.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {

    List<VitalSign> findByDeviceIdOrderByTimestampDesc(String deviceId);

    List<VitalSign> findByDeviceId(String deviceId);
}