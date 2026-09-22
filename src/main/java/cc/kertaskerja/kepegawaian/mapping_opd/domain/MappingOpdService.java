package cc.kertaskerja.kepegawaian.mapping_opd.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MappingOpdService {
    private final MappingOpdRepository repository;

    public MappingOpdService(MappingOpdRepository mappingOpdRepository) {
        this.repository = mappingOpdRepository;
    }

    public List<MappingOpd> findAll() {
        return repository.findAll();
    }
}
