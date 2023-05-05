package br.com.gfelix.app.step;

import br.com.gfelix.app.entity.Produtos;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CSVItemProcessor implements ItemProcessor<Produtos, Produtos> {

    @Override
    public Produtos process(Produtos produtos) throws Exception {
        String codigo = produtos.getCodigo();
        System.out.printf("Processando transação [%s]....\n", codigo);

        return produtos;
    }
}
