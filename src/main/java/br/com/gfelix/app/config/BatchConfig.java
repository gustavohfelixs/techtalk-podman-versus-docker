package br.com.gfelix.app.config;


import br.com.gfelix.app.entity.Produtos;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job job (JobBuilderFactory jobBuilderFactory,
                    StepBuilderFactory stepBuilderFactory,
                    ItemReader<Produtos> itemReader,
                    ItemProcessor<Produtos, Produtos> itemProcessor,
                    ItemWriter<Produtos> itemWriter
     ) {
        Step step = stepBuilderFactory.get("Step Carrega Dados")
                .<Produtos, Produtos> chunk(50)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
        return jobBuilderFactory.get("Job Carrega Step")
                .start(step)
                .build();
        }
    @Bean
    public FlatFileItemReader<Produtos> temReader() {
        FlatFileItemReader<Produtos> reader = new FlatFileItemReader<Produtos>();
        reader.setResource(new FileSystemResource("src/main/resources/clienteFindLog.csv"));
        reader.setName("Leitor CSV");
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());

        return reader;
    }

    @Bean
    public LineMapper<Produtos> lineMapper() {
        DefaultLineMapper<Produtos> defaultLineMapper = new DefaultLineMapper<Produtos>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("produto","categoria","codigo","valor","fornecedor");

        BeanWrapperFieldSetMapper<Produtos> fieldSetMapper = new BeanWrapperFieldSetMapper<Produtos>();
        fieldSetMapper.setTargetType(Produtos.class);

        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        defaultLineMapper.setLineTokenizer(lineTokenizer);

        return  defaultLineMapper;
    }
}


