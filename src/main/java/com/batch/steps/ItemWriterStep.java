package com.batch.steps;

import com.batch.entities.PersonEntity;
import com.batch.service.IPersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ItemWriterStep implements Tasklet {
    @Autowired
    IPersonService personService;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("-------------------->Inicio del paso de ESCRITURA del archivo <---------------------------");

        List<PersonEntity> personEntityList = (List<PersonEntity>) chunkContext.getStepContext()
                        .getStepExecution()
                                .getJobExecution()
                                        .getExecutionContext()
                                                .get("personList");
        personEntityList.forEach(personEntity -> {
            if(personEntity!=null){
                log.info(personEntity.toString());
            }
        });
        personService.saveAll(personEntityList);
        log.info("-------------------->FIN del paso de ESCRITURA del archivo <---------------------------");

        return RepeatStatus.FINISHED;
    }
}
