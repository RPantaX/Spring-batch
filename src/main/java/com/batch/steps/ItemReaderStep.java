package com.batch.steps;

import com.batch.entities.PersonEntity;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j //para manejar logs
public class ItemReaderStep implements Tasklet {
    @Autowired
    private ResourceLoader resourceLoader;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("-------------------->Inicio del paso de lectura del archivo <---------------------------");

        // Crear un objeto CSVReaderBuilderWithSeparator y especificar el separador como coma
        Reader reader = new FileReader(resourceLoader.getResource("classpath:files/destination/persons.csv").getFile());

        //PARA LEER CSV
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',') //definimos el separador de los datos
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .withSkipLines(1)   //Ignorar la primera linea como encabezado
                .build();

        // Leer cada línea del archivo CSV y convertirla a un objeto Persona
        List<PersonEntity> personList = new ArrayList<>();
        String[] linea;
        while ((linea = csvReader.readNext()) != null){
            PersonEntity personEntity = new PersonEntity();
            personEntity.setName(linea[0]);
            personEntity.setLastName(linea[1]);
            personEntity.setAge(Integer.parseInt(linea[2]));
            //guardar en la lista
            personList.add(personEntity);
        }
        csvReader.close();
        reader.close();

        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("personList", personList);

        log.info("---------------> Fin del paso de lectura del archivo <--------------------");

        return RepeatStatus.FINISHED; //TENEMOS MAS COMO CONTINUE, CONTINUE IF, ETC.
    }
}
