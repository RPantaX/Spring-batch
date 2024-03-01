package com.batch.steps;

import com.batch.entities.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ItemProcessorStep implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("-------------------->Inicio del paso de PROCESAMIENTO del archivo <---------------------------");
        List<PersonEntity> personEntityList = (List<PersonEntity>) chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .get("personList");
        List<PersonEntity> personFinalList = personEntityList.stream().map(person->{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            person.setInsertionDate(formatter.format(LocalDateTime.now()));
            return person;
        }).collect(Collectors.toList()); //con java 17 en adelante lo hacemos con un .toList()

        chunkContext.getStepContext()
                        .getStepExecution()
                                .getJobExecution()
                                        .getExecutionContext()
                                                .put("personList", personFinalList);

        log.info("---------------> Fin del paso de PROCESAMIENTO <--------------------");
        return null;
    }
}
