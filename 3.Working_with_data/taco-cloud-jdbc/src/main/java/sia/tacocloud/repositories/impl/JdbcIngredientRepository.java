package sia.tacocloud.repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Ingredient;
import sia.tacocloud.repositories.IngredientRepository;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        return this.jdbcTemplate.query("select * from Ingredient", this::processIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return this.jdbcTemplate.queryForObject(
            "select * from Ingredient where id = ?", 
            new RowMapper<Ingredient>() {
                @Override
                public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return processIngredient(rs, rowNum);
                }
            },
            id
        );
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        this.jdbcTemplate.update(
            "insert into Ingredient(id, name, type) values(?, ?, ?)",
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getType().toString()
        );
        return ingredient;
    }

    private Ingredient processIngredient(ResultSet resultSet, int rowNum) throws SQLException {
        return new Ingredient(
            resultSet.getString("id"),
            resultSet.getString("name"),
            Ingredient.Type.valueOf(resultSet.getString("type"))
        );
    }
    
}
