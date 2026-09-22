package cc.kertaskerja.kepegawaian.mapping_opd.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MappingOpdRepository extends CrudRepository<MappingOpd, Long> {
    @NonNull
    List<MappingOpd> findAll();

    List<MappingOpd> findBySumber(String sumber);

    Optional<MappingOpd> findBySumberAndKodeSumber(
            String sumber,
            String kodeSumber
    );

    Optional<MappingOpd> findBySumberAndKodeMaster(
            String sumber,
            String kodeMaster
    );

    boolean existsBySumberAndKodeSumber(
            String sumber,
            String kodeSumber
    );

    boolean existsBySumberAndKodeMaster(
            String sumber,
            String kodeMaster
    );
}
