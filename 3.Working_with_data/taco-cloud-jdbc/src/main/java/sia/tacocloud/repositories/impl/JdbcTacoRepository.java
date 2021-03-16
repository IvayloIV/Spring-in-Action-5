package sia.tacocloud.repositories.impl;

import java.util.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Taco;
import sia.tacocloud.repositories.TacoRepository;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        Long tacoId = this.saveTaco(taco);

        for (String ingredient: taco.getIngredients()) {
            this.saveIngredientToTaco(ingredient, tacoId);
        }

        taco.setId(tacoId);
        return taco;
    }

    private Long saveTaco(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        );

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
            Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime()))
        );

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(psc, keyHolder);
        Number key = keyHolder.getKey();
        return key.longValue();
    }

    private void saveIngredientToTaco(String ingredient, Long tacoId) {
        this.jdbcTemplate.update(
            "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
            tacoId,
            ingredient
        );
    }
    
}
