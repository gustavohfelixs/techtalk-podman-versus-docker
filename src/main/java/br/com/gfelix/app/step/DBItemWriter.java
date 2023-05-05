package br.com.gfelix.app.step;

import br.com.gfelix.app.entity.Produtos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class DBItemWriter implements ItemWriter<Produtos> {
    @Qualifier("mySqlDatasource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Value(value = "classpath:sql/writer/inserirProdutos.sql")
    private Resource inserirProdutos;

    @Override
    public void write(List<? extends Produtos> produtos) throws Exception {
        produtos.stream().forEach(item -> {
            try {
                SqlParameterSource sqlParameterData = new BeanPropertySqlParameterSource(item);

                namedParameterJdbcTemplate.update(new String(FileCopyUtils.copyToByteArray(inserirProdutos.getInputStream())),
                        sqlParameterData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("Persistido na base Produtos: " + item);
        });
    }

}
