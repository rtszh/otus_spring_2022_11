package ru.otus.spring.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;


    public AuthorDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query(
                "SELECT id, name FROM author",
                new AuthorMapper()
        );
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        return jdbc.queryForObject(
                "SELECT id, name FROM author WHERE id = :id",
                params,
                new AuthorMapper()
        );
    }

    @Override
    public Author insert(AuthorDto authorDto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", authorDto.getName());


        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("INSERT INTO author (name) values (:name)",
                params,
                kh,
                new String[]{"id"}
        );

        return Author.builder()
                .id(kh.getKey().longValue())
                .name(authorDto.getName())
                .build();
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return Author.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }
}
