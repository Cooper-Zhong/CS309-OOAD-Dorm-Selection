package cs309_dorm_backend.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import cs309_dorm_backend.domain.Favorite;

public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    public List<Favorite> findAll();
}
