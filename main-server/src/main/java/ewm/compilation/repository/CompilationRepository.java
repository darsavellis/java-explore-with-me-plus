package ewm.compilation.repository;

import ewm.compilation.dto.CompilationEvent;
import ewm.compilation.dto.EmptyCompilation;
import ewm.compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query(value = "SELECT id, pinned, title FROM compilations", nativeQuery = true)
    List<EmptyCompilation> findAllByPinnedIs(Boolean pinned, Pageable pageRequest);

    @Query(value = "SELECT compilation_id, event_id FROM compilations_events", nativeQuery = true)
    List<CompilationEvent> getCompilationEventMapping();
}
