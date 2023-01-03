package ru.otus.spring.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query(
                "SELECT id, name FROM genre",
                new GenreMapper()
        );
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        return jdbc.queryForObject(
                "SELECT id, name FROM genre WHERE id = :id",
                params,
                new GenreMapper()
        );
    }

    @Override
    public Genre insert(GenreDto genreDto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genreDto.getName());

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("INSERT INTO genre (name) values (:name)",
                params,
                kh,
                new String[]{"id"}
        );

        return Genre.builder()
                .id(kh.getKey().longValue())
                .name(genreDto.getName())
                .build();
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return Genre.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }
}
